package org.srvhub.model.changedealstatus

import org.srvhub.model.Request
import java.time.LocalDateTime
import java.util.*

data class ChangeDealApplicationStatusEventRequest (
        val msgDateTime: String,
        val msgId: UUID,
        val originator: String,
        val payload: ChangeDealApplicationStatusEventPayload,
        val payloadType: String,
        val receiver: String
) : Request