package org.srvhub.model.dealapp

data class FinanceFormValue(
    val formId: String,
    val indicators: List<String>,
    val monetaryUnitType: String,
    val periods: List<String>,
    val values: List<Any>
)