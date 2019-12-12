package org.srvhub.model.commonresponse

data class CommonResponsePayload(
    val error: Any?,
    val originatorMsgId: String,
    val originatorMsgType: String,
    val result: String,
    val targetObjectId: String
)