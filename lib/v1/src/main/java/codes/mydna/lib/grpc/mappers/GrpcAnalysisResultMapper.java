package codes.mydna.lib.grpc.mappers;

import codes.mydna.lib.AnalysisResult;
import codes.mydna.lib.Dna;
import codes.mydna.lib.enums.Status;
import codes.mydna.lib.grpc.AnalysisResultProto;

import java.util.stream.Collectors;

public class GrpcAnalysisResultMapper {

    public static AnalysisResult fromGrpcAnalysisResult(AnalysisResultProto.AnalysisResult grpcResult){

        if(grpcResult == null)
            return null;

        Dna dna = new Dna();
        dna.setId(grpcResult.getDnaId());

        AnalysisResult result = new AnalysisResult();
        result.setAnalysisName(grpcResult.getAnalysisName());
        result.setTotalExecutionTime(grpcResult.getTotalExecutionTime());
        result.setAnalysisExecutionTime(grpcResult.getAnalysisExecutionTime());
        result.setStatus(Status.valueOf(grpcResult.getStatus()));
        result.setDna(dna);
        result.setGenes(grpcResult.getFoundGenesList()
                .stream()
                .map(GrpcFoundGeneMapper::fromGrpcFoundGene)
                .collect(Collectors.toList()));
        result.setEnzymes(grpcResult.getFoundEnzymesList()
                .stream()
                .map(GrpcFoundEnzymeMapper::fromGrpcFoundEnzyme)
                .collect(Collectors.toList()));

        return result;
    }

    public static AnalysisResultProto.AnalysisResult toGrpcAnalysisResult(AnalysisResult result) {

        if(result == null)
            return AnalysisResultProto.AnalysisResult.newBuilder().build();

        return AnalysisResultProto.AnalysisResult.newBuilder()
                .setAnalysisName(result.getAnalysisName())
                .setTotalExecutionTime(result.getTotalExecutionTime())
                .setAnalysisExecutionTime(result.getAnalysisExecutionTime())
                .setDnaId(result.getDna().getId())
                .setStatus(result.getStatus().name())
                .addAllFoundGenes(result.getGenes()
                        .stream()
                        .map(GrpcFoundGeneMapper::toGrpcFoundGene)
                        .collect(Collectors.toList()))
                .addAllFoundEnzymes(result.getEnzymes()
                        .stream()
                        .map(GrpcFoundEnzymeMapper::toGrpcFoundEnzyme)
                        .collect(Collectors.toList()))
                .build();
    }
    
}
