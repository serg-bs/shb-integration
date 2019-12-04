package org.acme.rest.dealapp

data class Tender(
    val currencyType: String,
    val enforceAmount: Any,
    val federalLaw: String,
    val initialContractPrice: Any,
    val isLotQuantityUndefined: Any,
    val isMultiCustomer: Any,
    val isMultiLot: Any,
    val lotName: Any,
    val lotNumber: Any,
    val name: Any,
    val okpds: List<String>,
    val okveds: List<String>,
    val placementType: String,
    val publishDateTime: Any,
    val registryNumber: String,
    val url: Any,
    val winnerDateTime: Any,
    val winnerProtocol: Any
)