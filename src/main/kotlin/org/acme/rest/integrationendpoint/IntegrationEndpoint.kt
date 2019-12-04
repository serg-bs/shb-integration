package org.acme.rest.integrationendpoint

import org.acme.rest.integrationendpoint.Endpoint

data class IntegrationEndpoint(
    val endpoint: Endpoint,
    val applicationTransferMode: String?
)