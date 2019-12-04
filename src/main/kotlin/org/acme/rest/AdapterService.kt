package org.acme.rest

import org.acme.rest.openApi.ChangeDealApplicationStatusEvent
import org.acme.rest.dealapp.AdapterDealApplicationByIdFilter
import org.acme.rest.integrationendpoint.IntegrationEndpoint
import org.acme.rest.openApi.OpenApiRequest
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
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
    fun getDealById(@HeaderParam("Authorization") authorization: String?, params: AdapterDealApplicationByIdFilter): Object

    @POST
    @Path("/external/openapi/in")
    @Produces("application/json")
    @Consumes ("application/json")
    fun openApi(@HeaderParam("Authorization") authorization: String?, params: OpenApiRequest)


}