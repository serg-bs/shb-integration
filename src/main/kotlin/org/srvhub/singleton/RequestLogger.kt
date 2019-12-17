package org.srvhub.singleton

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.time.LocalDateTime
import java.util.*


object RequestLogger {
    fun add(type: Type, targetObjectId: UUID, payloadType: String, result: String?) {
        val text = "${type} ${payloadType} targetObject=${targetObjectId} ${result} \n"
        File("log.txt").appendText(text)
    }
}
