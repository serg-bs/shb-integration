package org.srvhub.model.updateapplicationparams

import org.srvhub.model.Request
import org.srvhub.model.dealappid.AddDealApplicationPayload
import java.util.*

data class UpdateApplicationParamsRequest(
        override val msgDateTime: String,
        override val msgId: UUID,
        override val originator: String,
        val payload: UpdateApplicationParams,
        override val payloadType: String,
        override val receiver: String
) : Request(msgDateTime, msgId, originator, payloadType, receiver)