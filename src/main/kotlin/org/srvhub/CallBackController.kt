package org.srvhub

import io.vertx.core.logging.LoggerFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.srvhub.model.Request
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEvent
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventRequest
import org.srvhub.model.commonresponse.CommonResponse
import org.srvhub.model.commonresponse.CommonResponseRequest
import org.srvhub.model.dealapp.AddDealApplicationRequest
import org.srvhub.model.dealappid.AddDealApplicationPayloadRequest
import org.srvhub.model.updateapplicationparams.UpdateApplicationParams
import org.srvhub.model.updateapplicationparams.UpdateApplicationParamsRequest
import org.srvhub.services.AdapterService
import org.srvhub.tmp.MockService
import org.srvhub.singleton.CredentialManager
import org.srvhub.singleton.RequestLogger
import org.srvhub.singleton.Type
import java.time.LocalDateTime
import java.util.*
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/callback")
class CallBackController {

    @Inject
    @field: RestClient
    lateinit var adapterService: AdapterService

    private val logger = LoggerFactory.getLogger(javaClass)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun callback(@Context sec: SecurityContext, request: Request): CommonResponseRequest {

        if (request is AddDealApplicationPayloadRequest) {
            RequestLogger.add(Type.RESPONSE, request.payload.dealApplicationId, request.payloadType, null)
            logger.info("Мы получили id=${request.payload.dealApplicationId} заявки, начнем обрабатывать ее ассинхронно")
            GlobalScope.async {
                по_id_получить_сделку_и_установить_статус(request)
                println("Закончили ассинхронно обрабатывать заявку")
            }

            println("Вернем common response не дожидаясь обработки")
            return getCommonResponse(request, "SUCCESS", request.payload.dealApplicationId)
        } else if (request is CommonResponseRequest) {
            RequestLogger.add(Type.RESPONSE, request.payload.targetObjectId, request.payload.originatorMsgType, request.payload.result)
            logger.info("Пришел ответ на сообщение ${request.payload.originatorMsgType}")
            if (request.payload.originatorMsgType == "ChangeDealApplicationStatusEvent") {
                logger.info("Ответ изменения статус сделки ${request.payload.targetObjectId}, результат=${request.payload.result}")
                GlobalScope.async {
                    присвоить_сделке_номер(request)
                }
            } else if (request.payload.originatorMsgType == "UpdateApplicationParams") {
                logger.info("Ответ изменения внешнего номера сделки ${request.payload.targetObjectId}, результат=${request.payload.result}")
            } else {
                logger.error("ХМ. мы не знаем об этом типе ${request.payload.originatorMsgType}")
            }
            return getCommonResponse(request, "SUCCESS", request.payload.targetObjectId)
        } else if (request is ChangeDealApplicationStatusEventRequest) {
            println("Поменялся статус заявки в goodfin id=${request.payload.dealApplicationId} ,status=${request.payload.status}")
            RequestLogger.add(Type.RESPONSE, request.payload.dealApplicationId, request.payloadType, request.payload.status)
            return getCommonResponse(request, "SUCCESS", request.payload.dealApplicationId)
        }
        throw IllegalArgumentException("Не умею обрабатывать такое сообщение")
    }

    private fun getCommonResponse(request: Request, result: String, targetObjectId: UUID): CommonResponseRequest {
        val commonResponse = CommonResponse(error = null,
                originatorMsgId = request.msgId,
                originatorMsgType = request.payloadType,
                result = result,
                targetObjectId = targetObjectId)

        return CommonResponseRequest(msgDateTime = LocalDateTime.now().toString(),
                msgId = UUID.randomUUID(),
                originator = request.receiver,
                payload = commonResponse,
                payloadType = commonResponse.javaClass.simpleName,
                receiver = "Shb")
    }

    suspend fun по_id_получить_сделку_и_установить_статус(request: AddDealApplicationPayloadRequest) {
        val dealApplicationId = request.payload.dealApplicationId
        val credential = CredentialManager.getCredential(request.receiver)
        val filter = AddDealApplicationRequest(dealApplicationId, "LIST", arrayListOf("FinForm"))

        RequestLogger.add(Type.REQUEST, dealApplicationId, filter.javaClass.simpleName, "синхронный вызов")
        val applicationDeal = adapterService.getDealById(credential!!.token, filter)
        logger.info("Запросили заявку id=${dealApplicationId} по выбранному фильтру. Вернули name=${applicationDeal.requestData.result.name}")

        val changeDealApplicationStatusEvent = ChangeDealApplicationStatusEvent(dealApplicationId = dealApplicationId,
                message = "Смена статуса, вот так мне захотелось", params = null, status = "my_new_status")
        val changeDealApplicationStatusEventRequest = ChangeDealApplicationStatusEventRequest(msgDateTime = LocalDateTime.now().toString(),
                msgId = UUID.randomUUID(),
                originator = request.receiver,
                payload = changeDealApplicationStatusEvent,
                payloadType = changeDealApplicationStatusEvent.javaClass.simpleName,
                receiver = "Shb")

        RequestLogger.add(Type.REQUEST, dealApplicationId, changeDealApplicationStatusEvent.javaClass.simpleName, null)
        adapterService.openApi(credential.token, changeDealApplicationStatusEventRequest)
    }

    suspend fun присвоить_сделке_номер(request: CommonResponseRequest) {
        val responsePayload = UpdateApplicationParams(applicationNumber = "Service" + UUID.randomUUID(),
                dealApplicationId = request.payload.targetObjectId)
        val updateApplicationParamsRequest = UpdateApplicationParamsRequest(msgDateTime = LocalDateTime.now().toString(),
                msgId = UUID.randomUUID(),
                originator = request.receiver,
                payload = responsePayload,
                payloadType = responsePayload.javaClass.simpleName,
                receiver = "Shb")
        val credential = CredentialManager.getCredential(request.receiver)
        RequestLogger.add(Type.REQUEST, request.payload.targetObjectId, responsePayload.javaClass.simpleName, null)
        adapterService.openApi(credential!!.token, updateApplicationParamsRequest)
    }
}