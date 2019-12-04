package org.acme.rest.statusMapping

data class Status(
    val dealApplicationStatus: String?,
    val displayStatus: String,
    val userActions: List<UserAction>?
)