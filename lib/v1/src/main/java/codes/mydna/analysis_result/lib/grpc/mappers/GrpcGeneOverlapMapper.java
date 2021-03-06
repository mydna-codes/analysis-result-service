package codes.mydna.analysis_result.lib.grpc.mappers;

import codes.mydna.analysis_result.lib.GeneOverlap;
import codes.mydna.analysis_result.lib.enums.Orientation;
import codes.mydna.analysis_result.lib.grpc.AnalysisResultProto;

public class GrpcGeneOverlapMapper {

    public static GeneOverlap fromGrpcGeneOverlap(AnalysisResultProto.GeneOverlap grpcOverlap){

        if(grpcOverlap == null)
            return null;

        GeneOverlap overlap = new GeneOverlap();
        overlap.setFromIndex(grpcOverlap.getFromIndex());
        overlap.setToIndex(grpcOverlap.getToIndex());
        overlap.setOrientation(Orientation.valueOf(grpcOverlap.getOrientation()));
        return overlap;
    }

    public static AnalysisResultProto.GeneOverlap toGrpcGeneOverlap(GeneOverlap overlap) {

        if(overlap == null)
            return AnalysisResultProto.GeneOverlap.newBuilder().build();

        return AnalysisResultProto.GeneOverlap.newBuilder()
                .setFromIndex(overlap.getFromIndex())
                .setToIndex(overlap.getToIndex())
                .setOrientation(overlap.getOrientation().name())
                .build();
    }

}
