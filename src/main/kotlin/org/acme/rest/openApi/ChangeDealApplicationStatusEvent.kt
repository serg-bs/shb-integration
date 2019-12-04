package org.acme.rest.openApi

data class ChangeDealApplicationStatusEvent(
    val dealApplicationId: String,
    val message: String,
    val params: String?,
    val status: String
)