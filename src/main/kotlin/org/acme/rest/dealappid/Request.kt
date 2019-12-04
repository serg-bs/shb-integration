package org.acme.rest.dealappid

import java.lang.reflect.Constructor
import javax.json.bind.annotation.JsonbCreator
import javax.json.bind.annotation.JsonbProperty

data class Request @JsonbCreator constructor(
    @JsonbProperty("msgDateTime") val msgDateTime: String,
    @JsonbProperty("msgId") val msgId: String,
    @JsonbProperty("originator") val originator: String,
    @JsonbProperty("payload") val payload: Any,
    @JsonbProperty("payloadType") val payloadType: String,
    @JsonbProperty("receiver") val receiver: String
)