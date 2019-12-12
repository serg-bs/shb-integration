package org.srvhub.services

import org.srvhub.model.EmptyRequest
import org.srvhub.model.generatedeals.GenerateDealRequest
import org.srvhub.model.integrationendpoint.IntegrationEndpoint
import org.srvhub.model.statusmapping.StatusMappingRequest
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.srvhub.model.auth.Registration
import org.srvhub.model.auth.Token
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*

@ApplicationScoped
@RegisterRestClient
interface SandboxService {
    @POST
//    @Path("/api/sandbox/registerservice")
    @Path("/sandbox/registerservice")
    @Produces("application/json")
    fun registerService(data: EmptyRequest?): Registration

    @POST
//    @Path("/api/oauth/token")
    @Path("/oauth/token")
    @Consumes("application/x-www-form-urlencoded")
    fun authorization(@HeaderParam("Authorization") authorization: String?, params: String): Token

    @POST
    @Path("/ad/external/integrationendpoint/update")
    @Produces("application/json")
    fun integrationEndpointUpdate(@HeaderParam("Authorization") authorization: String?, params: IntegrationEndpoint)

    @POST
    @Path("/service/statusmapping/update")
    @Produces("application/json")
    fun statusMappingUpdate(@HeaderParam("Authorization") authorization: String?, params: StatusMappingRequest)

    @POST
    @Path("/api/service/errormapping/update")
    @Produces("application/json")
    fun errorMappingUpdate(@HeaderParam("Authorization") authorization: String?, params: StatusMappingRequest)

    @POST
    @Path("/sandbox/generatedeals")
    @Produces("application/json")
    fun generateDeals(@HeaderParam("Authorization") authorization: String?, params: GenerateDealRequest)



}