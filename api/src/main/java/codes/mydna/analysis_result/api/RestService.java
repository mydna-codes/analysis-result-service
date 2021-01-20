package codes.mydna.analysis_result.api;

import codes.mydna.auth.common.RealmRole;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@DeclareRoles({RealmRole.ADMIN, RealmRole.PRO_USER, RealmRole.USER})
@ApplicationPath("v1")
public class RestService extends Application {

}
