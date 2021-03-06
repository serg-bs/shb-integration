package org.srvhub

import io.vertx.core.logging.LoggerFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.srvhub.model.ApplicationDocumentFile
import org.srvhub.model.Empty
import org.srvhub.model.Request
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEvent
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventRequest
import org.srvhub.model.commonresponse.CommonResponse
import org.srvhub.model.commonresponse.CommonResponseRequest
import org.srvhub.model.dealapp.AddDealApplicationRequest
import org.srvhub.model.dealapp.AddDealApplicationResponse
import org.srvhub.model.dealapp.ApplicationDocumentBranch
import org.srvhub.model.dealappid.AddDealApplicationPayloadRequest
import org.srvhub.model.updateapplicationparams.UpdateApplicationParams
import org.srvhub.model.updateapplicationparams.UpdateApplicationParamsRequest
import org.srvhub.services.AdapterService
import org.srvhub.services.SandboxService
import org.srvhub.singleton.CredentialManager
import java.io.File
import java.io.InputStream
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

    @Inject
    @field: RestClient
    lateinit var sandboxService: SandboxService

    private val logger = LoggerFactory.getLogger(javaClass)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun callback(@Context sec: SecurityContext, request: Request): Empty {

        if (request is AddDealApplicationPayloadRequest) {
            logger.info("Request from shb. AddDealApplicationPayloadRequest targetObject=${request.payload.dealApplicationId}")
            GlobalScope.launch {
                val commonResponse = getCommonResponse(request, "SUCCESS", request.payload.dealApplicationId)
                val credential = CredentialManager.getCredential(request.receiver)!!
                adapterService.openApi(credential.token, commonResponse)
                logger.info("Response to shb. AddDealApplicationPayloadRequest(CommonResponse) targetObject=${request.payload.dealApplicationId}")

                присвоить_сделке_номер(request.payload.dealApplicationId, request.receiver)

                изменить_статус_сделки(dealApplicationId = request.payload.dealApplicationId, originator = request.receiver)
                val dealApplicationResponse = синхронно_по_dealid_получить_сделку(request, arrayListOf(FetchedFieldsName.ApplicationDocumentFile, FetchedFieldsName.ApplicationDocumentBranch))

                val clientDocuments = filterClientDocs(dealApplicationResponse.requestData.result.clientDocuments, dealApplicationResponse.requestData.fetchFields.ApplicationDocumentFile)
                clientDocuments?.forEach {
                    получить_докуметы(it, originator = request.receiver)
                    узнать_тип_докумета(it.id, dealApplicationResponse.requestData.fetchFields.ApplicationDocumentBranch)
                }

                val dealDocuments = filterClientDocs(dealApplicationResponse.requestData.result.dealDocuments, dealApplicationResponse.requestData.fetchFields.ApplicationDocumentFile)
                dealDocuments?.forEach {
                    получить_докуметы(it, originator = request.receiver)
                    узнать_тип_докумета(it.id, dealApplicationResponse.requestData.fetchFields.ApplicationDocumentBranch)
                }
            }
        } else if (request is CommonResponseRequest) {
            logger.info("Response  from shb. ${request.payload.originatorMsgType}(CommonResponse) targetObject=${request.payload.targetObjectId}, result=${request.payload.result}")
            if (request.payload.result != "SUCCESS") {
                изменить_статус_сделки(dealApplicationId = request.payload.targetObjectId, originator = request.receiver)
            }
        } else if (request is ChangeDealApplicationStatusEventRequest) {
            logger.info("Response from shb. ChangeDealApplicationStatusEventRequest targetObject=${request.payload.dealApplicationId}, result=${request.payload.status}")
        } else {
            throw IllegalArgumentException("Не умею обрабатывать такое сообщение")
        }
        return Empty()
    }

    private fun узнать_тип_докумета(id: String, applicationDocumentBranch: List<ApplicationDocumentBranch>?) {
        if (applicationDocumentBranch != null) {
            for (docBranch in applicationDocumentBranch)
                if (id.startsWith(docBranch.id)) {
                    logger.info("Тип документа= ${docBranch.description}, documentId=${id}")
                }
        }
    }

    private fun filterClientDocs(docIds: List<UUID>, allDocument: List<ApplicationDocumentFile>?): List<ApplicationDocumentFile>? {
        val isClientDocument: (String) -> Boolean = {
            docIds.stream().anyMatch { docPackagesId -> it.contains(docPackagesId.toString()) }
        }
        return allDocument?.filter { it -> isClientDocument(it.id) }?.toList()
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

    suspend fun синхронно_по_dealid_получить_сделку(request: AddDealApplicationPayloadRequest, fetchFields: List<FetchedFieldsName>): AddDealApplicationResponse {
        val dealApplicationId = request.payload.dealApplicationId
        val originator = request.receiver
        val credential = CredentialManager.getCredential(originator)

        val filter = AddDealApplicationRequest(dealApplicationId, "LIST", fetchFields)

        val applicationDeal = adapterService.getDealById(credential!!.token, filter)
        logger.info("Request(Rest синхронный) to shb. Запросили заявку id=${dealApplicationId} по выбранному фильтру. Вернули name=${applicationDeal.requestData.result.name}")
        return applicationDeal
    }

    private fun изменить_статус_сделки(dealApplicationId: UUID, originator: String) {
        val credential = CredentialManager.getCredential(originator)!!
        val changeDealApplicationStatusEvent = ChangeDealApplicationStatusEvent(dealApplicationId = dealApplicationId,
                message = "Смена статуса, вот так мне захотелось", params = null, status = "my_new_status")
        val changeDealApplicationStatusEventRequest = ChangeDealApplicationStatusEventRequest(msgDateTime = LocalDateTime.now().toString(),
                msgId = UUID.randomUUID(),
                originator = originator,
                payload = changeDealApplicationStatusEvent,
                payloadType = changeDealApplicationStatusEvent.javaClass.simpleName,
                receiver = "Shb")

        logger.info("Request to shb. ChangeDealApplicationStatusEvent targetObject=${dealApplicationId}")
        adapterService.openApi(credential.token, changeDealApplicationStatusEventRequest)
    }

    suspend fun присвоить_сделке_номер(dealApplicationId: UUID, originator: String) {
        val responsePayload = UpdateApplicationParams(applicationNumber = "Service" + UUID.randomUUID(),
                dealApplicationId = dealApplicationId)
        val updateApplicationParamsRequest = UpdateApplicationParamsRequest(msgDateTime = LocalDateTime.now().toString(),
                msgId = UUID.randomUUID(),
                originator = originator,
                payload = responsePayload,
                payloadType = responsePayload.javaClass.simpleName,
                receiver = "Shb")
        val credential = CredentialManager.getCredential(originator)
        logger.info("Request to shb. UpdateApplicationParamsRequest targetObject=${dealApplicationId}")
        adapterService.openApi(credential!!.token, updateApplicationParamsRequest)
    }

    suspend fun получить_докуметы(docFile: ApplicationDocumentFile, originator: String) {
        val documentId = docFile.id
        val credential = CredentialManager.getCredential(originator)!!
        val documentFile = sandboxService.getDocumentFile(credential.token, documentId)
        if (documentFile.entity is InputStream) {
            val entity: InputStream = documentFile.entity as InputStream
            logger.info("Получен Файл ${docFile.fileName}, documentId=${docFile.id}")
            entity.toFile(docFile.fileName)
        }

    }

    fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }
}