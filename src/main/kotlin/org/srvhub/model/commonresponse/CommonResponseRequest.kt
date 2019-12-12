package org.srvhub.model.commonresponse

import org.srvhub.model.Request
import java.util.*

data class CommonResponseRequest(
        val msgDateTime: String,
        val msgId: UUID,
        val originator: String,
        val payload: CommonResponsePayload,
        val payloadType: String,
        val receiver: String
) :Request