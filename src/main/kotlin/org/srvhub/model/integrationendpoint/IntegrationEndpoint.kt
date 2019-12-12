package org.srvhub.model.integrationendpoint

data class IntegrationEndpoint(
        val endpoint: Endpoint,
        val applicationTransferMode: String?
)