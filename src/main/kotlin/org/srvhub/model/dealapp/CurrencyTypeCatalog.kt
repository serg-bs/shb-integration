package org.srvhub.model.dealapp

data class CurrencyTypeCatalog(
    val code: String,
    val codeNum: String,
    val id: String,
    val name: String,
    val parentId: String?,
    val state: String?
)