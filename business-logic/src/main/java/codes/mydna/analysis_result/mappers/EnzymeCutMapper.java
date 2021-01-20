package codes.mydna.analysis_result.mappers;

import codes.mydna.analysis_result.entities.EnzymeCutEntity;
import codes.mydna.analysis_result.lib.EnzymeCut;

public class EnzymeCutMapper {

    public static EnzymeCut fromEntity(EnzymeCutEntity entity){
        if (entity == null)
            return null;
        EnzymeCut enzymeCut = new EnzymeCut();
        enzymeCut.setUpperCut(entity.getUpperCut());
        enzymeCut.setLowerCut(entity.getLowerCut());
        return enzymeCut;
    }
    public static EnzymeCutEntity toEntity(EnzymeCut cut){
        if(cut == null)
            return null;
        EnzymeCutEntity entity = new EnzymeCutEntity();
        entity.setUpperCut(cut.getUpperCut());
        entity.setLowerCut(cut.getLowerCut());
        return entity;
    }

}
