package codes.mydna.analysis_result.lib.grpc.mappers;

import codes.mydna.sequence_bank.lib.Enzyme;
import codes.mydna.analysis_result.lib.FoundEnzyme;
import codes.mydna.analysis_result.lib.grpc.AnalysisResultProto;

import java.util.stream.Collectors;

public class GrpcFoundEnzymeMapper {

    public static FoundEnzyme fromGrpcFoundEnzyme(AnalysisResultProto.FoundEnzyme grpcFoundEnzyme){

        if(grpcFoundEnzyme == null)
            return null;

        Enzyme enzyme = new Enzyme();
        enzyme.setId(grpcFoundEnzyme.getEnzymeId());

        FoundEnzyme foundEnzyme = new FoundEnzyme();
        foundEnzyme.setEnzyme(enzyme);
        foundEnzyme.setCuts(grpcFoundEnzyme.getCutsList()
                .stream()
                .map(GrpcEnzymeCutMapper::fromGrpcEnzymeCut)
                .collect(Collectors.toList()));

        return foundEnzyme;
    }

    public static AnalysisResultProto.FoundEnzyme toGrpcFoundEnzyme(FoundEnzyme foundEnzyme) {

        if(foundEnzyme == null || foundEnzyme.getEnzyme() == null)
            return AnalysisResultProto.FoundEnzyme.newBuilder().build();

        return AnalysisResultProto.FoundEnzyme.newBuilder()
                .setEnzymeId(foundEnzyme.getEnzyme().getId())
                .addAllCuts(foundEnzyme.getCuts()
                        .stream()
                        .map(GrpcEnzymeCutMapper::toGrpcEnzymeCut)
                        .collect(Collectors.toList()))
                .build();
    }

}
