package codes.mydna.analysis_result.lib;

import codes.mydna.analysis_result.lib.enums.Status;

public class AnalysisResultSummary extends BaseType {

    private String analysisName;
    private int totalExecutionTime;
    private int analysisExecutionTime;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public int getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public void setTotalExecutionTime(int totalExecutionTime) {
        this.totalExecutionTime = totalExecutionTime;
    }

    public int getAnalysisExecutionTime() {
        return analysisExecutionTime;
    }

    public void setAnalysisExecutionTime(int analysisExecutionTime) {
        this.analysisExecutionTime = analysisExecutionTime;
    }
}
