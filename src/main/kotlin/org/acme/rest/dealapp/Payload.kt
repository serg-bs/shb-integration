package org.acme.rest.dealapp

data class Payload(
    val createApplicationDateTime: String,
    val dealApplicationId: String,
    val requestData: RequestData
)