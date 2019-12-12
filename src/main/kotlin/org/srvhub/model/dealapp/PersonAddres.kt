package org.srvhub.model.dealapp

data class PersonAddres(
    val addressString: String,
    val addressType: String,
    val building: String?,
    val city: String,
    val country: String?,
    val district: String,
    val fias: String?,
    val flat: String?,
    val house: String,
    val id: String,
    val isPresent: String?,
    val kladr: String?,
    val postCode: String,
    val region: String,
    val stateInDraft: String?,
    val street: String
)