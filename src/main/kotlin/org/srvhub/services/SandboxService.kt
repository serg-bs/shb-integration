package org.srvhub.services

import org.srvhub.model.Empty
import org.srvhub.model.generatedeals.GenerateDealRequest
import org.srvhub.model.integrationendpoint.IntegrationEndpoint
import org.srvhub.model.statusmapping.StatusMappingRequest
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.annotations.jaxrs.QueryParam
import org.srvhub.model.auth.Registration
import org.srvhub.model.auth.Token
import org.srvhub.model.integrationlog.IntegrationLogFilter
import java.lang.Boolean
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
@RegisterRestClient
interface SandboxService {
    @POST
//    @Path("/api/sandbox/registerservice")
    @Path("/sandbox/registerservice")
    @Produces("application/json")
    fun registerService(data: Empty?): Registration

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

    @GET
    @Path("/document/downloaddocumentfile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    fun getDocumentFile(@HeaderParam("Authorization") authorization: String?, @QueryParam documentFileId: String, @QueryParam isInline: java.lang.Boolean= Boolean(true)): Response

    @POST
    @Path("/sandbox/generatedeals")
    @Produces("application/json")
    fun generateDeals(@HeaderParam("Authorization") authorization: String?, params: GenerateDealRequest)


    @POST
    @Path("/sandbox/integrationlogs")
    @Produces("application/json")
    fun getIntegrationLog(@HeaderParam("Authorization") authorization: String?, params: IntegrationLogFilter): Any

}