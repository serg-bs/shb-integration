package org.srvhub;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ClientLoggingRequestFilter implements ClientRequestFilter {

    private static final Logger LOG = Logger.getLogger(ClientLoggingRequestFilter.class);

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        LOG.info("\nClient request ${context.uri.path} \n" + objectMapper.writeValueAsString(context.getEntity()));
    }
}