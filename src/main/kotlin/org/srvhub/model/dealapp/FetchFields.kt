package org.srvhub.model.dealapp

import org.srvhub.model.ApplicationDocumentFile
import org.srvhub.model.ApplicationDocumentFileHash

data class FetchFields(
        val ApplicationDocument: List<ApplicationDocument>?,
        val ApplicationDocumentBranch: List<ApplicationDocumentBranch>?,
        val ApplicationDocumentFile: List<ApplicationDocumentFile>?,
        val ApplicationDocumentFileHash: List<ApplicationDocumentFileHash>?,
        val BankingDetail: List<BankingDetail>?,
        val Company: List<Company>?,
        val CompanyAddress: List<CompanyAddres>?,
        val CompanyCommunication: List<Any>?,
        val CompanyLicense: List<CompanyLicense>?,
        val CompanySourceInfo: List<CompanySourceInfo>?,
        val CompanySystemTypeCatalog: List<CompanySystemTypeCatalog>?,
        val CurrencyTypeCatalog: List<CurrencyTypeCatalog>?,
        val DealAddCategory: List<DealAddCategory>?,
        val DealAddField: List<Any>?,
        val DocumentRoot: List<DocumentRoot>?,
        val FederalLawTypeCatalog: List<FederalLawTypeCatalog>?,
        val FinanceForm: List<FinanceForm>?,
        val FinanceFormValues: List<FinanceFormValue>?,
        val FinanceIndicator: List<FinanceIndicator>?,
        val FinancePeriod: List<FinancePeriod>?,
        val LegalFounder: List<LegalFounder>?,
        val LegalTypeCatalog: List<LegalTypeCatalog>?,
        val ModifierParticipant: List<ModifierParticipant>?,
        val OkatoCatalog: List<Any>?,
        val OkfsCatalog: List<Any>?,
        val OkoguCatalog: List<OkoguCatalog>?,
        val OkopfCatalog: List<OkopfCatalog>?,
        val OkpdCatalog: List<OkpdCatalog>?,
        val OksmCatalog: List<OksmCatalog>?,
        val OktmoCatalog: List<Any>?,
        val OkvedCatalog: List<OkvedCatalog>?,
        val Person: List<Person>?,
        val PersonAddress: List<PersonAddres>?,
        val PersonRole: List<PersonRole>?,
        val PlacementTypeCatalog: List<PlacementTypeCatalog>?,
        val TaxSystemCatalog: List<TaxSystemCatalog>?,
        val TopTypeCatalog: List<Any>?,
        val UserAssignment: List<UserAssignment>?,
        val ClientDocuments: List<Any>?,
        val DealDocuments: List<Any>?

)