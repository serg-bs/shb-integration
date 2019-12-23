package org.srvhub

import io.vertx.core.logging.LoggerFactory
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.resteasy.annotations.jaxrs.PathParam
import org.srvhub.model.Empty
import org.srvhub.model.auth.Token
import org.srvhub.model.generatedeals.GenerateDealRequest
import org.srvhub.model.integrationendpoint.Authentication
import org.srvhub.model.integrationendpoint.Endpoint
import org.srvhub.model.integrationendpoint.IntegrationEndpoint
import org.srvhub.model.integrationlog.IntegrationLogFilter
import org.srvhub.model.statusmapping.Status
import org.srvhub.model.statusmapping.StatusMapping
import org.srvhub.model.statusmapping.StatusMappingRequest
import org.srvhub.services.AdapterService
import org.srvhub.services.SandboxService
import org.srvhub.singleton.Credential
import org.srvhub.singleton.CredentialManager
import java.util.*
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/init")
class InitController {
    val baseUrl = "http://192.168.4.56:8082/callback"

    @ConfigProperty(name = "quarkus.http.port")
    var port: String? = null

    @Inject
    @field: RestClient
    lateinit var sandboxService: SandboxService

    @Inject
    @field: RestClient
    lateinit var adapterService: AdapterService

    private val logger = LoggerFactory.getLogger(javaClass)

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_HTML)
    fun init(): String {
        val emptyRequest = Empty()
        logger.info("dfsdfadfasdf")
        var registration = sandboxService.registerService(emptyRequest)
        logger.info("Мы получили login=${registration.serviceSystemName}, пароль=${registration.password}")

        val credential = "Basic " + Base64.getEncoder().encodeToString((registration.serviceSystemName + ":" + registration.password).toByteArray(Charsets.UTF_8))
        val authorization = sandboxService.authorization(credential, "grant_type=client_credentials")
        val token = "Bearer " + authorization.access_token
        CredentialManager.registerCredential(Credential(registration.serviceSystemName!!, registration.password!!, token))
        logger.info("Токен login=${registration.serviceSystemName}, токен=${token}")

        val authentication = Authentication(type = "BASIC", userName = "QQQ1", password = "WWW1")

        val endpoint = Endpoint(authentication, baseUrl = baseUrl, type = "HTTP")
        val integrationEndpoint = IntegrationEndpoint(endpoint, "ID")
        adapterService.integrationEndpointUpdate(token, integrationEndpoint)
        logger.info("Зарегистрировали callback endpoint")

        statusMappingUpdate(authorization)

        sandboxService.generateDeals(token, GenerateDealRequest("1"))

        val linkIntegrationLog = "http://localhost:${port}/init/integrationlogs/${registration.serviceSystemName}"
        return "Поздравляю! login=${registration.serviceSystemName}, пароль=${registration.password} <br>вы зарегистрировались в системе Goodfin! <br> " +
                "Ваш токен ${credential} <br> <br>" +
                "Url для обратного вызова ${baseUrl} <br>" +
                "Ендпоинт для отладки " +
                " <a href=\"${linkIntegrationLog}\">${linkIntegrationLog}</a><br>" +
                "Установлен status mapping<br>" +
                "Сгенерировали тестовые сделки."
    }

    private fun statusMappingUpdate(authorization: Token) {
        val statusNew = Status(dealApplicationStatus = null, displayStatus = "Новая", userActions = null)
        val statusCancel = Status(dealApplicationStatus = null, displayStatus = "Отменена", userActions = null)
        val statusMapping = StatusMapping(my_new_status = statusNew, my_cancel_status = statusCancel)
        val statusMappingObject = StatusMappingRequest(statusMapping)
        sandboxService.statusMappingUpdate("Bearer " + authorization.access_token, statusMappingObject)
        logger.info("Зарегистрировали status mapping для изменений статусов по заявке")
    }

    @GET
    @Path("/integrationlogs/{sysVal}")
    @Produces(MediaType.APPLICATION_JSON)
    fun test(@PathParam("sysVal") sysVal: String): Any {
        val credential = CredentialManager.getCredential(sysVal)
        return sandboxService.getIntegrationLog(credential!!.token, IntegrationLogFilter())
    }
}




