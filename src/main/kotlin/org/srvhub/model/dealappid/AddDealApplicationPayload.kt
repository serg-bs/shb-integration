package org.srvhub.model.dealappid

import java.util.*

data class AddDealApplicationPayload (
        val createApplicationDateTime: String,
        val dealApplicationId: UUID,
        val requestData: Any?
)