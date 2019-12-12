package org.srvhub.model.dealapp

data class ProductDealState(
        val actualContractPrice: String?,
        val amount: Int,
        val concludedContracts: String?,
        val customer: Customer,
        val deadlineForGrant: String?,
        val deliveryIsRequired: String?,
        val endDate: String,
        val hasLoss: String?,
        val isIndisputableWriteOff: String?,
        val isPrepayment: String?,
        val isQuickDecision: String?,
        val isWarrantyPeriod: String?,
        val loanCommitments: String?,
        val prepaymentAmount: String?,
        val productType: String,
        val startDate: String,
        val tender: Tender,
        val termDays: Int
)