package org.acme.rest

import EmptyRequest
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.acme.rest.dealapp.AdapterDealApplicationByIdFilter
import org.acme.rest.dealappid.Request
import org.acme.rest.generateDeals.GenerateDealRequest
import org.acme.rest.integrationendpoint.Authentication
import org.acme.rest.integrationendpoint.Endpoint
import org.acme.rest.integrationendpoint.IntegrationEndpoint
import org.acme.rest.openApi.ChangeDealApplicationStatusEvent
import org.acme.rest.openApi.OpenApiRequest
import org.acme.rest.statusMapping.Status
import org.acme.rest.statusMapping.StatusMapping
import org.acme.rest.statusMapping.StatusMappingRequest
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.time.LocalDateTime
import java.util.*
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext
import kotlin.collections.HashMap

@Path("/hello")
class HelloEndpoint {

    @Inject
    @field: RestClient
    lateinit var sandboxService: SandboxService

    @Inject
    @field: RestClient
    lateinit var adapterService: AdapterService

    @Inject
    @field: RestClient
    lateinit var mockService: MockService

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun hello(): String {
        val d: EmptyRequest = EmptyRequest()
            var v = sandboxService.registerService(d)

        val credential = "Basic " + Base64.getEncoder().encodeToString((v.serviceSystemName + ":" + v.password).toByteArray(Charsets.UTF_8))
        val authorization = sandboxService.authorization(credential, "grant_type=client_credentials")
        val token = "Bearer " + authorization.access_token
        DataProviderManager.registerDataProvider(DataProvider(v.serviceSystemName!!, v.password!!, token))

        val authentication = Authentication(type = "BASIC", userName = "QQQ1", password = "WWW1")
        val endpoint = Endpoint(authentication, baseUrl = "http://192.168.4.56:8082/hello/import", type = "HTTP")
//        val endpoint = Endpoint(authentication, baseUrl = "http://192.168.4.56:8083/test2", type = "HTTP", responseMode = "APPLICATIONDEALID")
        val integrationEndpoint = IntegrationEndpoint(endpoint, null)


        adapterService.integrationEndpointUpdate(token, integrationEndpoint)

        val status = Status(dealApplicationStatus = null, displayStatus = "Новая", userActions = null)
        val statusCancel = Status(dealApplicationStatus = null, displayStatus = "Новая", userActions = null)
        val statusMapping = StatusMapping(SERVICE_CREATE_STATUS = status, SERVICE_CANCEL_STATUS = statusCancel)
        val statusMappingObject = StatusMappingRequest(statusMapping)
        sandboxService.statusMappingUpdate("Bearer " + authorization.access_token, statusMappingObject)

        sandboxService.generateDeals(token, GenerateDealRequest("1"))


        return "Hello From Microprofile Kotlin"
    }
    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass)

    @POST
    @Path("/import")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun dealApplicationId(@Context sec: SecurityContext, request : Request) {

        if (request.payloadType == "AddDealApplication"){
            try {
//                final Jsonb jsonb = JsonbBuilder.create();
//                jsonb.
                val dealApplicationId = ""//request.payload.dealApplicationId
                val dataProvider = DataProviderManager.getDataProvider(request.receiver)

                val filter = AdapterDealApplicationByIdFilter(dealApplicationId, "LIST", arrayListOf("FinForm"))

                val dealById = adapterService.getDealById(dataProvider!!.token, filter)
                val changeDealApplicationStatusEvent = ChangeDealApplicationStatusEvent(dealApplicationId = dealApplicationId,
                        message = "Смена статуса, вот так мне захотелось", params = null, status = "SERVICE_CREATE_STATUS")
                val openApiRequest = OpenApiRequest(msgDateTime = LocalDateTime.now(),
                        msgId = UUID.randomUUID(),
                        originator = request.receiver,
                        payload = changeDealApplicationStatusEvent,
                        payloadType = "ChangeDealApplicationStatusEvent",
                        receiver = "Shb")


                adapterService.openApi(dataProvider!!.token, openApiRequest)
                println(dealById)
                println(filter)
            } catch (e: Exception){
                e.printStackTrace()
            }

        }
    }
}

object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        allDataProviders.put(provider.serviceSystemName, provider)
    }

    val allDataProviders: MutableMap<String, DataProvider> = HashMap<String, DataProvider>()

    fun getDataProvider(serviceSystemName: String): DataProvider? {
        val dataProvider = allDataProviders[serviceSystemName]
        return dataProvider
    }
}
