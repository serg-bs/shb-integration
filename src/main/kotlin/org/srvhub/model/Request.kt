package org.srvhub.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.srvhub.model.dealappid.AddDealApplicationPayload
import org.srvhub.model.changedealstatus.ChangeDealApplicationStatusEventRequest
import org.srvhub.model.commonresponse.CommonResponseRequest
import org.srvhub.model.dealappid.AddDealApplicationPayloadRequest

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "payloadType", visible = true)
@JsonSubTypes(JsonSubTypes.Type(value = ChangeDealApplicationStatusEventRequest::class, name = "ChangeDealApplicationStatusEvent"),
        JsonSubTypes.Type(value = AddDealApplicationPayloadRequest::class, name = "AddDealApplication"),
        JsonSubTypes.Type(value = CommonResponseRequest::class, name = "CommonResponse")
)
open interface Request