package org.srvhub.model.statusmapping

// Здесь прописываются статусы интегратора
data class StatusMapping(
        val my_new_status: Status?,
        val my_cancel_status: Status?
)