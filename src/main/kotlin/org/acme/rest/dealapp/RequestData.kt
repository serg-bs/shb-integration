package org.acme.rest.dealapp

data class RequestData(
    val fetchFields: FetchFields,
    val result: Result
)