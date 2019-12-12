package org.srvhub.model.dealapp

data class FinancePeriod(
    val disabled: Boolean,
    val endDate: String,
    val id: String,
    val monetaryUnit: String
)