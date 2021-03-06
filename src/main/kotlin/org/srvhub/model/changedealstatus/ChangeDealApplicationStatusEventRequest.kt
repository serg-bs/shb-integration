package org.srvhub.model.changedealstatus

import org.srvhub.model.Request
import java.util.*

data class ChangeDealApplicationStatusEventRequest(
        override val msgDateTime: String,
        override val msgId: UUID,
        override val originator: String,
        val payload: ChangeDealApplicationStatusEvent,
        override val payloadType: String,
        override val receiver: String
) : Request(msgDateTime, msgId, originator, payloadType, receiver)