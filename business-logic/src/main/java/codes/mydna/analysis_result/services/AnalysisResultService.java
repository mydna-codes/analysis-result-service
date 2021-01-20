package codes.mydna.analysis_result.services;

import codes.mydna.analysis_result.lib.AnalysisResult;
import codes.mydna.analysis_result.lib.AnalysisResultSummary;
import codes.mydna.auth.common.models.User;
import codes.mydna.rest.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.analysis_result.services.impl.AnalysisResultServiceImpl
 */
public interface AnalysisResultService {

    EntityList<AnalysisResultSummary> getAnalysisResultSummaries(QueryParameters qp, User user);
    AnalysisResult getAnalysisResult(String id, User user);
    AnalysisResult insertAnalysisResult(AnalysisResult result, User user, boolean notifyUser);
    boolean removeAnalysisResult(String id, User user);

}
