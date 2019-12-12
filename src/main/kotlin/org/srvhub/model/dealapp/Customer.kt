package org.srvhub.model.dealapp

data class Customer(
    val bankAccountCorr: String?,
    val bankAccountMain: String?,
    val bankBik: String?,
    val bankName: String?,
    val consolidatedRegNum: String?,
    val contactEmail: String?,
    val contactFax: String?,
    val contactFio: String?,
    val contactPhone: String?,
    val factAddress: String?,
    val inn: String,
    val kpp: String,
    val name: String,
    val ogrn: String?,
    val oktmo: String?,
    val postAddress: String?,
    val regNum: String?,
    val region: String?,
    val siteUrl: String?
)