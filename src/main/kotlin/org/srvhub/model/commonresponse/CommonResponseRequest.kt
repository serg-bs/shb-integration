package org.srvhub.model.commonresponse

import org.srvhub.model.Request
import java.util.*

data class CommonResponseRequest(
        override val msgDateTime: String,
        override val msgId: UUID,
        override val originator: String,
        val payload: CommonResponse,
        override val payloadType: String,
        override val receiver: String
) :Request(msgDateTime, msgId, originator, payloadType, receiver)
