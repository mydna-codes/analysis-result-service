package codes.mydna.api.resources;

import codes.mydna.auth.common.models.User;
import codes.mydna.auth.keycloak.KeycloakContext;
import codes.mydna.http.Headers;
import codes.mydna.lib.AnalysisResultSummary;
import codes.mydna.services.AnalysisResultService;
import codes.mydna.utils.EntityList;
import codes.mydna.utils.QueryParametersBuilder;
import codes.mydna.utils.QueryUtil;
import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("results")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecureResource
@RequestScoped
public class AnalysisResultResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private AnalysisResultService analysisResultService;

    @Inject
    private KeycloakContext keycloakContext;

    private User user;

    @PostConstruct
    private void fetchUser(){
        user = keycloakContext.getUser();
    }

    @GET
    @AuthenticatedAllowed
    public Response getAnalysisResultSummaries(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getQuery());
        EntityList<AnalysisResultSummary> summaries = analysisResultService.getAnalysisResultSummaries(qp, user);
        return Response.ok()
                .entity(summaries.getList())
                .header(Headers.XTotalCount, summaries.getCount())
                .build();
    }

    @DELETE
    @Path("{id}")
    @AuthenticatedAllowed
    public Response deleteAnalysisReport(@PathParam("id") String id){
        return Response.ok()
                .entity(analysisResultService.removeAnalysisResult(id, user))
                .build();
    }

}