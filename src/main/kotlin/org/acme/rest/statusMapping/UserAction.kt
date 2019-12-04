package org.acme.rest.statusMapping

data class UserAction(
    val actionType: String,
    val description: String,
    val label: String,
    val template: String
)