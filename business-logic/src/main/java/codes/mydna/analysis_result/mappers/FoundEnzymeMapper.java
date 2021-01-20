package codes.mydna.analysis_result.mappers;

import codes.mydna.analysis_result.entities.FoundEnzymeEntity;
import codes.mydna.sequence_bank.lib.Enzyme;
import codes.mydna.analysis_result.lib.FoundEnzyme;

import java.util.stream.Collectors;

public class FoundEnzymeMapper {

    public static FoundEnzyme fromEntity(FoundEnzymeEntity entity) {
        if (entity == null)
            return null;
        Enzyme enzyme = new Enzyme();
        enzyme.setId(entity.getEnzymeId());
        FoundEnzyme foundEnzyme = new FoundEnzyme();
        foundEnzyme.setEnzyme(enzyme);
        foundEnzyme.setCuts(entity.getEnzymeCuts()
                .stream()
                .map(EnzymeCutMapper::fromEntity)
                .collect(Collectors.toList()));
        return foundEnzyme;
    }

    public static FoundEnzymeEntity toEntity(FoundEnzyme foundEnzyme){
        if(foundEnzyme == null)
            return null;
        FoundEnzymeEntity entity = new FoundEnzymeEntity();
        entity.setEnzymeId(foundEnzyme.getEnzyme().getId());
        entity.setEnzymeCuts(foundEnzyme.getCuts()
                .stream()
                .map(EnzymeCutMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }

}
