package org.srvhub.model.dealapp

data class OkvedCatalog(
    val code: String,
    val id: String,
    val name: String,
    val parentId: String?,
    val state: String?
)