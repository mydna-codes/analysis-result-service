package codes.mydna.analysis_result.lib;

import codes.mydna.analysis_result.lib.enums.Status;
import codes.mydna.sequence_bank.lib.Dna;

import java.util.List;

public class AnalysisResult extends BaseType {

    private Status status;

    private String analysisName;
    private int totalExecutionTime;
    private int analysisExecutionTime;

    private Dna dna;
    private List<FoundEnzyme> enzymes;
    private List<FoundGene> genes;

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

    public Dna getDna() {
        return dna;
    }

    public void setDna(Dna dna) {
        this.dna = dna;
    }

    public List<FoundEnzyme> getEnzymes() {
        return enzymes;
    }

    public void setEnzymes(List<FoundEnzyme> enzymes) {
        this.enzymes = enzymes;
    }

    public List<FoundGene> getGenes() {
        return genes;
    }

    public void setGenes(List<FoundGene> genes) {
        this.genes = genes;
    }
}
