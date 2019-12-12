package org.srvhub.model.dealapp

data class BankingDetail(
    val actualAddress: String?,
    val bankName: String,
    val bik: String,
    val corrAccount: String?,
    val emails: String?,
    val faxes: String?,
    val id: String,
    val inn: String?,
    val isMainAccount: String?,
    val kbk: String?,
    val kpp: String?,
    val legalAddress: String?,
    val paymentAccount: String,
    val phones: String?,
    val shortBankName: String?,
    val stateInDraft: String?,
    val typeBankingDetails: String?,
    val ufk: String?,
    val website: String?
)