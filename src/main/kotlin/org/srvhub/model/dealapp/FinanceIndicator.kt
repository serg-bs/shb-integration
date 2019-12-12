package org.srvhub.model.dealapp

data class FinanceIndicator(
    val code: String?,
    val id: String,
    val indicatorType: String,
    val isCalculated: Boolean,
    val isNegative: Boolean,
    val name: String,
    val order: String,
    val parent: String?
)