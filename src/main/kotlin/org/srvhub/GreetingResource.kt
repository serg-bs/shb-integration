package org.srvhub

import org.srvhub.model.EmptyRequest
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.srvhub.model.Request
import org.srvhub.model.generatedeals.GenerateDealRequest
import org.srvhub.model.integrationendpoint.Authentication
import org.srvhub.model.integrationendpoint.Endpoint
import org.srvhub.model.integrationendpoint.IntegrationEndpoint
import org.srvhub.model.statusmapping.Status
import org.srvhub.model.statusmapping.StatusMapping
import org.srvhub.model.statusmapping.StatusMappingRequest
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.srvhub.model.dealapp.AddDealApplicationRequest
import org.srvhub.model.dealappid.AddDealApplicationPayloadRequest
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventPayload
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventRequest
import org.srvhub.model.commonresponse.CommonResponsePayload
import org.srvhub.model.commonresponse.CommonResponseRequest
import org.srvhub.services.AdapterService
import org.srvhub.services.MockService
import org.srvhub.services.SandboxService
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
class IntegrationEndpoint {

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
        val emptyRequest = EmptyRequest()
        var v = sandboxService.registerService(emptyRequest)

        val credential = "Basic " + Base64.getEncoder().encodeToString((v.serviceSystemName + ":" + v.password).toByteArray(Charsets.UTF_8))
        val authorization = sandboxService.authorization(credential, "grant_type=client_credentials")
        val token = "Bearer " + authorization.access_token
        DataProviderManager.registerDataProvider(DataProvider(v.serviceSystemName!!, v.password!!, token))

        val authentication = Authentication(type = "BASIC", userName = "QQQ1", password = "WWW1")
        val endpoint = Endpoint(authentication, baseUrl = "http://192.168.4.56:8082/hello/import", type = "HTTP")
//        val endpoint = Endpoint(authentication, baseUrl = "http://192.168.4.56:8083/test2", type = "HTTP", responseMode = "APPLICATIONDEALID")
        val integrationEndpoint = IntegrationEndpoint(endpoint, null)

        adapterService.integrationEndpointUpdate(token, integrationEndpoint)

        val statusNew = Status(dealApplicationStatus = null, displayStatus = "Новая", userActions = null)
        val statusCancel = Status(dealApplicationStatus = null, displayStatus = "Отменена", userActions = null)
        val statusMapping = StatusMapping(MY_NEW_STATUS = statusNew, MY_CANCEL_STATUS = statusCancel)
        val statusMappingObject = StatusMappingRequest(statusMapping)
        sandboxService.statusMappingUpdate("Bearer " + authorization.access_token, statusMappingObject)

        sandboxService.generateDeals(token, GenerateDealRequest("1"))

        return "Hello From Microprofile Kotlin"
    }

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass)

    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun test(request: Request) : String {
        print(request)
        return "Hello test"
    }

    @POST
    @Path("/import")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun dealApplicationId(@Context sec: SecurityContext, request: Request)  {

        if (request is AddDealApplicationPayloadRequest ) {
            try {

                val dealApplicationId = UUID.fromString(request.payload.dealApplicationId)

                val dataProvider = DataProviderManager.getDataProvider(request.receiver)

                val filter = AddDealApplicationRequest(dealApplicationId, "LIST", arrayListOf("FinForm"))

                val dealById = adapterService.getDealById(dataProvider!!.token, filter)

                val commonResponsePayload = CommonResponsePayload(error = null, originatorMsgId = request.msgId, originatorMsgType = "AddDealApplication", result = "SUCCESS", targetObjectId = request.payload.dealApplicationId)
                val commonRequest = CommonResponseRequest(msgDateTime = LocalDateTime.now().toString(),
                        msgId = UUID.randomUUID(),
                        originator = request.receiver,
                        payload = commonResponsePayload,
                        payloadType = "ChangeDealApplicationStatusEvent",
                        receiver = "Shb")

                adapterService.openApi(dataProvider.token, commonRequest)


                val changeDealApplicationStatusEvent = ChangeDealApplicationStatusEventPayload(dealApplicationId = dealApplicationId,
                        message = "Смена статуса, вот так мне захотелось", params = null, status = "SERVICE_CREATE_STATUS")
                val openApiRequest = ChangeDealApplicationStatusEventRequest(msgDateTime = LocalDateTime.now().toString(),
                        msgId = UUID.randomUUID(),
                        originator = request.receiver,
                        payload = changeDealApplicationStatusEvent,
                        payloadType = "ChangeDealApplicationStatusEvent",
                        receiver = "Shb")



                adapterService.openApi(dataProvider.token, openApiRequest)
                println(dealById)
                println(filter)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else if (request is CommonResponseRequest) {
            val targetObjectId = request.payload.targetObjectId
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
