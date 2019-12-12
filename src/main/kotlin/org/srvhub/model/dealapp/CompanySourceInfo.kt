package org.srvhub.model.dealapp

data class CompanySourceInfo(
    val hasBranches: Boolean,
    val headCompany: Boolean,
    val id: String,
    val source: String?
)