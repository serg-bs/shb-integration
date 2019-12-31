package org.srvhub.model.dealapp

import java.util.*

data class Result(
        val agent: String?,
        val author: String,
        val baseOnType: String,
        val baseOnValue: String,
        val client: String,
        val clientContact: String,
        val clientContactEmail: String,
        val clientContactPhone: String,
        val clientDocuments: List<UUID>,
        val currencyType: String,
        val dealDocuments: List<UUID>,
        val dealNumber: String,
        val dealProbability: String?,
        val financeForms: Any?,
        val id: String,
        val income: String?,
        val isFirstDealClient: String?,
        val name: String,
        val pipeline: String?,
        val priority: String,
        val productDealState: ProductDealState,
        val resolutionDateTime: String?,
        val responsible: String?
)