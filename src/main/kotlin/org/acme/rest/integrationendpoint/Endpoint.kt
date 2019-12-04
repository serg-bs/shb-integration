package org.acme.rest.integrationendpoint

data class Endpoint(
        val authentication: Authentication,
        val baseUrl: String,
        val type: String
)