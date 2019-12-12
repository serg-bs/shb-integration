package org.srvhub.services

import org.srvhub.model.dealapp.AddDealApplicationRequest
import org.srvhub.model.dealapp.AddDealApplicationResponse
import org.srvhub.model.integrationendpoint.IntegrationEndpoint
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.srvhub.model.Request
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*

@ApplicationScoped
@RegisterRestClient
interface AdapterService {

    @POST
    @Path("/external/integrationendpoint/update")
    @Produces("application/json")
    fun integrationEndpointUpdate(@HeaderParam("Authorization") authorization: String?, params: IntegrationEndpoint)

    @POST
    @Path("/external/dealapplication/byid")
    @Produces("application/json")
    fun getDealById(@HeaderParam("Authorization") authorization: String?, params: AddDealApplicationRequest): AddDealApplicationResponse

    @POST
    @Path("/external/openapi/in")
    @Produces("application/json")
    @Consumes ("application/json")
    fun openApi(@HeaderParam("Authorization") authorization: String?, params: Request)


}