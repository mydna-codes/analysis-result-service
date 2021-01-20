package codes.mydna.analysis_result.mappers;

import codes.mydna.analysis_result.entities.FoundGeneEntity;
import codes.mydna.analysis_result.lib.FoundGene;
import codes.mydna.sequence_bank.lib.Gene;

import java.util.stream.Collectors;

public class FoundGeneMapper {
    public static FoundGene fromEntity(FoundGeneEntity entity) {
        if (entity == null)
            return null;
        Gene gene = new Gene();
        gene.setId(entity.getId());
        FoundGene foundGene = new FoundGene();
        foundGene.setGene(gene);
        foundGene.setOverlaps(entity.getGeneOverlaps()
                .stream()
                .map(GeneOverlapMapper::fromEntity)
                .collect(Collectors.toList()));
        return foundGene;
    }
    public static FoundGeneEntity toEntity(FoundGene foundGene){
        if(foundGene == null)
            return null;
        FoundGeneEntity entity = new FoundGeneEntity();
        entity.setGeneId(foundGene.getGene().getId());
        entity.setGeneOverlaps(foundGene.getOverlaps()
                .stream()
                .map(GeneOverlapMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }

}
