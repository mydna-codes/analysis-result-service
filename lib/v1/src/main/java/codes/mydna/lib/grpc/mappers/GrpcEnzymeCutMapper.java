package codes.mydna.lib.grpc.mappers;

import codes.mydna.lib.EnzymeCut;
import codes.mydna.lib.grpc.AnalysisResultProto;

public class GrpcEnzymeCutMapper {

    public static EnzymeCut fromGrpcEnzymeCut(AnalysisResultProto.EnzymeCut grpcCut){

        if(grpcCut == null)
            return null;

        EnzymeCut cut = new EnzymeCut();
        cut.setUpperCut(grpcCut.getUpperCut());
        cut.setLowerCut(grpcCut.getLowerCut());
        return cut;
    }

    public static AnalysisResultProto.EnzymeCut toGrpcEnzymeCut(EnzymeCut cut) {

        if(cut == null)
            return AnalysisResultProto.EnzymeCut.newBuilder().build();

        return AnalysisResultProto.EnzymeCut.newBuilder()
                .setUpperCut(cut.getUpperCut())
                .setLowerCut(cut.getUpperCut())
                .build();
    }
    
}
