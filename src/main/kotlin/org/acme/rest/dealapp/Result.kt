package org.acme.rest.dealapp

data class Result(
    val agent: Any,
    val author: String,
    val baseOnType: String,
    val baseOnValue: String,
    val client: String,
    val clientContact: String,
    val clientContactEmail: String,
    val clientContactPhone: String,
    val clientDocuments: List<Any>,
    val currencyType: String,
    val dealDocuments: List<Any>,
    val dealNumber: String,
    val dealProbability: Any,
    val financeForms: List<FinanceFormX>,
    val id: String,
    val income: Any,
    val isFirstDealClient: Any,
    val name: String,
    val pipeline: Any,
    val priority: String,
    val productDealState: ProductDealState,
    val resolutionDateTime: Any,
    val responsible: String
)