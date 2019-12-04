package org.acme.rest.openApi

import java.time.LocalDateTime
import java.util.*

data class OpenApiRequest(
        val msgDateTime: LocalDateTime,
        val msgId: UUID,
        val originator: String,
        val payload: ChangeDealApplicationStatusEvent,
        val payloadType: String,
        val receiver: String

)