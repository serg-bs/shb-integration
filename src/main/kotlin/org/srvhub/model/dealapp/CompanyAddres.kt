package org.srvhub.model.dealapp

data class CompanyAddres(
        val addressString: String,
        val addressType: String,
        val building: String?,
        val city: String,
        val country: String?,
        val district: String?,
        val fias: String?,
        val flat: String?,
        val house: String,
        val id: String,
        val isOwned: Boolean,
        val isPost: String?,
        val isPresent: Boolean,
        val kladr: String?,
        val postCode: String,
        val region: String,
        val rentOrOwnerContract: RentOrOwnerContract,
        val stateInDraft: String?,
        val street: String
)