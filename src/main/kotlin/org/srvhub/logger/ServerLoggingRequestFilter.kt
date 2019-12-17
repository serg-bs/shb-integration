package org.srvhub.logger

import io.vertx.core.http.HttpServerRequest
import org.apache.commons.io.IOUtils
import org.apache.commons.io.output.ByteArrayOutputStream
import org.jboss.logging.Logger
import java.io.BufferedReader
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.Provider

@Provider
class ServerLoggingRequestFilter : ContainerRequestFilter {
    @Context
    var info: UriInfo? = null
    @Context
    var request: HttpServerRequest? = null

    override fun filter(context: ContainerRequestContext) {
        var stream = context.entityStream
        val baos = ByteArrayOutputStream()
        IOUtils.copy(stream, baos)
        val content = baos.toInputStream().bufferedReader().use(BufferedReader::readText)
        LOG.info("\nServer request ${info!!.path} \n" + content)
        context.entityStream = baos.toInputStream()
    }

    companion object {
        private val LOG = Logger.getLogger(ServerLoggingRequestFilter::class.java)
    }
}