package org.srvhub.model.dealapp

data class AddDealApplicationResponse(
    val createApplicationDateTime: String?,
    val dealApplicationId: String,
    val requestData: RequestData
)