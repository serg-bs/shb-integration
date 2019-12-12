package org.srvhub.model.dealapp

data class OksmCatalog(
    val alpha2: String,
    val alpha3: String,
    val code: String,
    val fullName: String,
    val id: String,
    val name: String,
    val parentId: String?,
    val state: String?
)