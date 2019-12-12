package org.srvhub.model.statusmapping

data class UserAction(
    val actionType: String,
    val description: String,
    val label: String,
    val template: String
)