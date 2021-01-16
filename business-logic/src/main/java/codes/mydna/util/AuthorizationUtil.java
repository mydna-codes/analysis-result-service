package codes.mydna.util;

import codes.mydna.auth.common.models.User;
import codes.mydna.entities.AnalysisResultEntity;
import codes.mydna.exceptions.UnauthorizedException;

public class AuthorizationUtil {

    public static void verifyOwner(AnalysisResultEntity entity, User user){

        if(!entity.getOwnerId().equals(user.getId())) {
            throw new UnauthorizedException();
        }

    }

}
