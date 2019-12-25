package org.srvhub.model.dealapp

import org.srvhub.FetchedFieldsName
import java.util.*

data class AddDealApplicationRequest (
    val dealApplicationId: UUID,
    val fetchedFieldMode: String,
    val fetchFields: List<FetchedFieldsName>
)