package org.srvhub

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import org.apache.commons.io.output.ByteArrayOutputStream
import org.jboss.logging.Logger
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.inject.Inject
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter
import javax.ws.rs.ext.Provider


@Provider
class ClientLoggingResponseFilter : ClientResponseFilter {
    @Inject
    var objectMapper: ObjectMapper? = null

    @Throws(IOException::class)
    override fun filter(containerRequestContext: ClientRequestContext, context: ClientResponseContext) {
        var stream = context.entityStream
        val baos = ByteArrayOutputStream()
        IOUtils.copy(stream, baos)
        val content = baos.toInputStream().bufferedReader().use(BufferedReader::readText)
        LOG.info("\nClient response ${containerRequestContext.uri.path} \n" + content)
        context.entityStream = baos.toInputStream()
    }

    companion object {
        private val LOG = Logger.getLogger(ClientLoggingResponseFilter::class.java)
    }
}