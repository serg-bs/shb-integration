package org.srvhub.model.dealapp

data class Tender(
    val currencyType: String,
    val enforceAmount: String?,
    val federalLaw: String,
    val initialContractPrice: String?,
    val isLotQuantityUndefined: String?,
    val isMultiCustomer: String?,
    val isMultiLot: String?,
    val lotName: String?,
    val lotNumber: String?,
    val name: String?,
    val okpds: List<String>,
    val okveds: List<String>,
    val placementType: String,
    val publishDateTime: String?,
    val registryNumber: String,
    val url: String?,
    val winnerDateTime: String?,
    val winnerProtocol: String?
)