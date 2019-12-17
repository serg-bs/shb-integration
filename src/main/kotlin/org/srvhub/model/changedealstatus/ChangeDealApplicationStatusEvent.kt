package org.srvhub.model.changedealstatus

import java.util.*

data class ChangeDealApplicationStatusEvent(
        val dealApplicationId: UUID,
        val message: String,
        val params: String?,
        val status: String
)