package org.srvhub

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.junit.jupiter.api.Test
import org.srvhub.model.Request
import org.srvhub.model.dealapp.AddDealApplicationResponse
import java.io.File

class FruitApplicationTests{
    @Test
    fun test() {
        val jsonAsString= File("/home/serg/IdeaProjects/rest-kotlin2/src/main/resources/1.json").readText(Charsets.UTF_8)

        val objectMapper = ObjectMapper();
        objectMapper.registerModule(ParameterNamesModule())
        val readValue = objectMapper.readValue(jsonAsString, AddDealApplicationResponse::class.java)
        print("Cool parsing done. name =${readValue}")
    }
}