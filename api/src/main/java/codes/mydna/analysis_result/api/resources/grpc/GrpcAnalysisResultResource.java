package codes.mydna.analysis_result.api.resources.grpc;

import codes.mydna.analysis_result.lib.AnalysisResult;
import codes.mydna.analysis_result.lib.grpc.AnalysisResultProto;
import codes.mydna.analysis_result.lib.grpc.AnalysisResultServiceGrpc;
import codes.mydna.analysis_result.lib.grpc.mappers.GrpcAnalysisResultMapper;
import codes.mydna.analysis_result.services.AnalysisResultService;
import codes.mydna.rest.exceptions.NotFoundException;
import codes.mydna.rest.exceptions.RestException;
import codes.mydna.rest.exceptions.UnauthorizedException;
import codes.mydna.sequence_bank.lib.grpc.mappers.GrpcUserMapper;
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

        LOG.info("Hello");

        AnalysisResultService analysisResultService = CDI.current().select(AnalysisResultService.class).get();

        try {

            boolean sendEmail = (request.getServiceType() == AnalysisResultProto.AnalysisResultInsertionRequest.ServiceType.LARGE_SCALE);

            AnalysisResult result = analysisResultService
                    .insertAnalysisResult(
                            GrpcAnalysisResultMapper.fromGrpcAnalysisResult(request.getAnalysisResult()),
                            GrpcUserMapper.fromGrpcUser(request.getUser()),
                            sendEmail);

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
            LOG.severe("INTERNAL SERVER ERROR");
            responseObserver.onError(Status.INTERNAL.asException());
        }

    }
}
