package org.acme.rest.dealapp

data class FinanceFormX(
    val actualPeriods: Boolean,
    val formId: String,
    val indicatorChoiceSelectedItem: IndicatorChoiceSelectedItem,
    val indicators: List<String>,
    val periods: List<String>,
    val values: List<Any>
)