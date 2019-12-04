package org.acme.rest.dealapp

data class ProductDealState(
    val actualContractPrice: Any,
    val amount: Int,
    val concludedContracts: Any,
    val customer: Customer,
    val deadlineForGrant: Any,
    val deliveryIsRequired: Any,
    val endDate: String,
    val hasLoss: Any,
    val isIndisputableWriteOff: Any,
    val isPrepayment: Any,
    val isQuickDecision: Any,
    val isWarrantyPeriod: Any,
    val loanCommitments: Any,
    val prepaymentAmount: Any,
    val productType: String,
    val startDate: String,
    val tender: Tender,
    val termDays: Int
)