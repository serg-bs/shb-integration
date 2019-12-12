package org.srvhub.model.dealapp

data class LegalFounder(
    val address: String,
    val id: String,
    val inn: String,
    val kpp: String,
    val name: String,
    val ogrn: String,
    val percent: Double,
    val stateInDraft: String?
)