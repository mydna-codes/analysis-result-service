package codes.mydna.api.resources.graphql;

import codes.mydna.auth.common.models.User;
import codes.mydna.auth.keycloak.KeycloakContext;
import codes.mydna.lib.AnalysisResult;
import codes.mydna.services.AnalysisResultService;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@GraphQLClass
@RequestScoped
public class GraphQLFetchAnalysisResource {

    public static final Logger LOG = Logger.getLogger(GraphQLFetchAnalysisResource.class.getSimpleName());

    @Inject
    private AnalysisResultService resultService;

    @Inject
    private KeycloakContext keycloakContext;

    private User user;

    @PostConstruct
    private void fetchUser() {
        user = keycloakContext.getUser();
    }

    @GraphQLMutation
    public AnalysisResult fetchAnalysis(
            @GraphQLNonNull
            @GraphQLArgument(name = "requestId", description = "Request id of the AnalysisResult that " +
                    "you want to fetch."
            ) String requestId) {

        return resultService.getAnalysisResult(requestId, user);
    }

}
