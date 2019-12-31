package org.srvhub.model

data class ApplicationDocumentFileHash(
    val hash: String,
    val hashAlgorithm: String,
    val id: String
)