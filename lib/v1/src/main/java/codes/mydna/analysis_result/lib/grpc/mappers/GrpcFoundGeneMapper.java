package codes.mydna.analysis_result.lib.grpc.mappers;

import codes.mydna.analysis_result.lib.FoundGene;
import codes.mydna.sequence_bank.lib.Gene;
import codes.mydna.analysis_result.lib.grpc.AnalysisResultProto;

import java.util.stream.Collectors;

public class GrpcFoundGeneMapper {

    public static FoundGene fromGrpcFoundGene(AnalysisResultProto.FoundGene grpcFoundGene){

        if(grpcFoundGene == null)
            return null;

        Gene gene = new Gene();
        gene.setId(grpcFoundGene.getGeneId());

        FoundGene foundGene = new FoundGene();
        foundGene.setGene(gene);
        foundGene.setOverlaps(grpcFoundGene.getOverlapsList()
                .stream()
                .map(GrpcGeneOverlapMapper::fromGrpcGeneOverlap)
                .collect(Collectors.toList()));

        return foundGene;
    }

    public static AnalysisResultProto.FoundGene toGrpcFoundGene(FoundGene foundGene) {

        if(foundGene == null || foundGene.getGene() == null)
            return AnalysisResultProto.FoundGene.newBuilder().build();

        return AnalysisResultProto.FoundGene.newBuilder()
                .setGeneId(foundGene.getGene().getId())
                .addAllOverlaps(foundGene.getOverlaps()
                        .stream()
                        .map(GrpcGeneOverlapMapper::toGrpcGeneOverlap)
                        .collect(Collectors.toList()))
                .build();
    }
    
}
