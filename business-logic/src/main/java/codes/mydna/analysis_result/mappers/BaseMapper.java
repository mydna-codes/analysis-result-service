package codes.mydna.analysis_result.mappers;

import codes.mydna.analysis_result.entities.BaseEntity;
import codes.mydna.analysis_result.lib.BaseType;

public class BaseMapper {

    public static void fromEntity(BaseEntity entity, BaseType baseType) {
        if(entity == null || baseType == null)
            return;
        baseType.setId(entity.getId());
        baseType.setCreated(entity.getCreated());
        baseType.setLastModified(entity.getLastModified());
    }

    // Don't convert from type to entity
}
