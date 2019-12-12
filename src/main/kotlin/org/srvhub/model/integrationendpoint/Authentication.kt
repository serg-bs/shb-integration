package org.srvhub.model.integrationendpoint

data class Authentication(
    val password: String,
    val type: String,
    val userName: String
)