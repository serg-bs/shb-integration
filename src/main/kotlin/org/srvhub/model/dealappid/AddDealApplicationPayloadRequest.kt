package org.srvhub.model.dealappid

import org.srvhub.model.Request
import java.util.*

data class AddDealApplicationPayloadRequest(
        override val msgDateTime: String,
        override val msgId: UUID,
        override val originator: String,
        val payload: AddDealApplicationPayload,
        override val payloadType: String,
        override val receiver: String
) : Request(msgDateTime, msgId, originator, payloadType, receiver)