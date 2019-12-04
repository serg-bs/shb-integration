package org.acme.rest.integrationendpoint

data class Authentication(
    val password: String,
    val type: String,
    val userName: String
)