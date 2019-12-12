package org.srvhub.model.dealappid

data class AddDealApplicationPayload (
        val createApplicationDateTime: String,
        val dealApplicationId: String,
        val requestData: Any?
)