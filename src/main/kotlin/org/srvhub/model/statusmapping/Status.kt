package org.srvhub.model.statusmapping

data class Status(
    val dealApplicationStatus: String?,
    val displayStatus: String,
    val userActions: List<UserAction>?
)