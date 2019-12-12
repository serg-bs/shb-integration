package org.srvhub.model.dealapp

data class CompanyLicense(
    val activity: String,
    val date: String,
    val endDate: String?,
    val id: String,
    val listActivities: String,
    val number: String,
    val place: String,
    val startDate: String?,
    val stateInDraft: String?,
    val who: String?
)