package org.srvhub.logger

import com.fasterxml.jackson.databind.ObjectMapper
import org.jboss.logging.Logger
import java.io.IOException
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class ServerLoggingResponseFilter : ContainerResponseFilter {
    @Inject
    lateinit var objectMapper: ObjectMapper

    @Throws(IOException::class)
    override fun filter(containerRequestContext: ContainerRequestContext, context: ContainerResponseContext) {
        LOG.info("\nServer response ${containerRequestContext.uriInfo.path} \n" + objectMapper.writeValueAsString(context.entity))
    }

    companion object {
        private val LOG = Logger.getLogger(ServerLoggingResponseFilter::class.java)
    }
}