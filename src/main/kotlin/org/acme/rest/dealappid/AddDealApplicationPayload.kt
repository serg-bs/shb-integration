package org.acme.rest.dealappid

import javax.json.bind.annotation.JsonbCreator
import javax.json.bind.annotation.JsonbProperty

data class AddDealApplicationPayload @JsonbCreator constructor(
        @JsonbProperty("createApplicationDateTime") val createApplicationDateTime: String,
        @JsonbProperty("dealApplicationId") val dealApplicationId: String,
        @JsonbProperty("requestData") val requestData: Any?
)