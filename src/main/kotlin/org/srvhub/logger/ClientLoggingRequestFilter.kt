package org.srvhub.logger

import com.fasterxml.jackson.databind.ObjectMapper
import org.jboss.logging.Logger
import java.io.IOException
import javax.inject.Inject
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.Provider

@Provider
class ClientLoggingRequestFilter : ClientRequestFilter {
    @Inject
    lateinit var objectMapper: ObjectMapper

    @Throws(IOException::class)
    override fun filter(context: ClientRequestContext) {
        LOG.info("\nClient request ${context.uri.path} \n" + objectMapper.writeValueAsString(context.entity))
    }

    companion object {
        private val LOG = Logger.getLogger(ClientLoggingRequestFilter::class.java)
    }
}