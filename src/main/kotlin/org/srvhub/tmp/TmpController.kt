package org.srvhub.tmp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.srvhub.model.Request
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/tmp")
class TmpController {

    @Inject
    @field: RestClient
    lateinit var mockService: MockService

    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    fun test(request: Request): String {
        print(request)
        return "Hello test"
    }
}


fun main() {
    println("Start")

// Start a coroutine
    GlobalScope.launch {
        delay(1000)
        println("Hello")
    }

//    Thread.sleep(2000) // wait for 2 seconds
    println("Stop")
}