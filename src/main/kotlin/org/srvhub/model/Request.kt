package org.srvhub.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventRequest
import org.srvhub.model.commonresponse.CommonResponseRequest
import org.srvhub.model.dealappid.AddDealApplicationPayloadRequest
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "payloadType", visible = true)
@JsonSubTypes(JsonSubTypes.Type(value = ChangeDealApplicationStatusEventRequest::class, name = "ChangeDealApplicationStatusEvent"),
        JsonSubTypes.Type(value = AddDealApplicationPayloadRequest::class, name = "AddDealApplication"),
        JsonSubTypes.Type(value = CommonResponseRequest::class, name = "CommonResponse")
)
open class Request(open val msgDateTime: String,
                   open val msgId: UUID,
                   open val originator: String,
                   open val payloadType: String,
                   open val receiver: String)