package org.acme.rest.dealapp

data class FetchFields(
    val CompanyContactParticipant: List<CompanyContactParticipant>,
    val FinanceForm: List<FinanceForm>,
    val UserAssignment: List<UserAssignment>
)