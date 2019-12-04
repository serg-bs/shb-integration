package org.acme.rest.dealapp

data class DealApp(
    val msgDateTime: String,
    val msgId: String,
    val originator: String,
    val payload: Payload,
    val payloadType: String,
    val `receiver`: String
)