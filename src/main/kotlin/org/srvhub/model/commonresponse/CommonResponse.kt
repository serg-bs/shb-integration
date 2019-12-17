package org.srvhub.model.commonresponse

import java.util.*

data class CommonResponse(
    val error: Any?,
    val originatorMsgId: UUID,
    val originatorMsgType: String,
    val result: String,
    val targetObjectId: UUID
)