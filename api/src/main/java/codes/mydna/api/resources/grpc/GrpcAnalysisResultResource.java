package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.NotFoundException;
import codes.mydna.exceptions.RestException;
import codes.mydna.exceptions.UnauthorizedException;
import codes.mydna.lib.AnalysisResult;
import codes.mydna.lib.grpc.AnalysisResultProto;
import codes.mydna.lib.grpc.AnalysisResultServiceGrpc;
import codes.mydna.lib.grpc.mappers.GrpcAnalysisResultMapper;
import codes.mydna.lib.grpc.mappers.GrpcUserMapper;
import codes.mydna.services.AnalysisResultService;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.logging.Logger;

@GrpcService
public class GrpcAnalysisResultResource extends AnalysisResultServiceGrpc.AnalysisResultServiceImplBase {

    public static final Logger LOG = Logger.getLogger(GrpcAnalysisResultResource.class.getName());

    @Override
    public void insertAnalysisResult(AnalysisResultProto.AnalysisResultInsertionRequest request,
                                     StreamObserver<AnalysisResultProto.AnalysisResultInsertionResponse> responseObserver) {

        AnalysisResultService analysisResultService = CDI.current().select(AnalysisResultService.class).get();

        try {

            AnalysisResult result = analysisResultService
                    .insertAnalysisResult(
                            GrpcAnalysisResultMapper.fromGrpcAnalysisResult(request.getAnalysisResult()),
                            GrpcUserMapper.fromGrpcUser(request.getUser()));

            responseObserver.onNext(AnalysisResultProto.AnalysisResultInsertionResponse
                    .newBuilder()
                    .setAnalysisResultId(result.getId())
                    .build());

            responseObserver.onCompleted();

        } catch (NotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        } catch (UnauthorizedException e) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException());
        } catch (RestException e) {
            responseObserver.onError(Status.INTERNAL.asException());
        }

    }
}
