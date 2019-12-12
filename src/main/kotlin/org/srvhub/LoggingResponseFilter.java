package org.srvhub;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.stream.Collectors;

@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(LoggingResponseFilter.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext context) throws IOException {

        MultivaluedMap<String, String> headers = context.getStringHeaders();
        String mapAsString = headers.keySet().stream()
                .map(key -> key + "=" + headers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();
        final String body = context.getEntity() !=null ? context.getEntity().toString(): "" ;

        LOG.infof("Request %s %s from IP %s %s", mapAsString, path, address, body);
    }

}