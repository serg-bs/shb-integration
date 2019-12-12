package org.srvhub.model.dealappid

import org.srvhub.model.Request
import java.util.*

data class AddDealApplicationPayloadRequest (
        val msgDateTime: String,
        val msgId: String,
        val originator: String,
        val payload: AddDealApplicationPayload,
        val payloadType: String,
        val receiver: String
) : Request