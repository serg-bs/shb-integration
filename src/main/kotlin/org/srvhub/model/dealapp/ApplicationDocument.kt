package org.srvhub.model.dealapp

data class ApplicationDocument(
    val author: String,
    val createDateTime: String,
    val description: String,
    val docState: String,
    val docType: String,
    val fileHash: String?,
    val fileName: String?,
    val fileType: String?,
    val id: String,
    val resolutionDateTime: String?
)

