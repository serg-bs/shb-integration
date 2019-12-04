package org.acme.rest.dealapp

data class AdapterDealApplicationByIdFilter (
    val dealApplicationId: String,
    val fetchedFieldMode: String,
    val fetchFields: List<String>
)