package org.srvhub.model.dealapp

import java.util.*

data class AddDealApplicationRequest (
    val dealApplicationId: UUID,
    val fetchedFieldMode: String,
    val fetchFields: List<String>
)