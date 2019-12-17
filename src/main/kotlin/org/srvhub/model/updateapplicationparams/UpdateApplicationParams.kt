package org.srvhub.model.updateapplicationparams

import java.util.*

data class UpdateApplicationParams(
    val applicationNumber: String,
    val dealApplicationId: UUID
)