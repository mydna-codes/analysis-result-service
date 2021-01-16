package codes.mydna.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ANALYSIS_RESULT_TABLE")
@NamedQueries(
        @NamedQuery(
                name = AnalysisResultEntity.GET_BY_ID_AND_OWNER_ID,
                query = "SELECT r FROM AnalysisResultEntity r WHERE r.ownerId = :owner_id AND r.id = :id"
        )
)
public class AnalysisResultEntity extends BaseEntity {

    public static final String GET_BY_ID_AND_OWNER_ID = "AnalysisResultEntity.GET_BY_ID_AND_OWNER_ID";

    @Column(name = "OWNER_ID")
    private String ownerId;

    @Column(name = "ANALYSIS_NAME")
    private String analysisName;

    @Column(name = "TOTAL_EXECUTION_TIME")
    private int totalExecutionTime;

    @Column(name = "ANALYSIS_EXECUTION_TIME")
    private int analysisExecutionTime;

    @Column(name = "DNA_ID")
    private String dnaId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANALYSIS_RESULT_ID")
    private List<FoundEnzymeEntity> foundEnzymes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANALYSIS_RESULT_ID")
    private List<FoundGeneEntity> foundGenes;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getDnaId() {
        return dnaId;
    }

    public void setDnaId(String dnaId) {
        this.dnaId = dnaId;
    }

    public List<FoundEnzymeEntity> getFoundEnzymes() {
        return foundEnzymes;
    }

    public void setFoundEnzymes(List<FoundEnzymeEntity> foundEnzymes) {
        this.foundEnzymes = foundEnzymes;
    }

    public List<FoundGeneEntity> getFoundGenes() {
        return foundGenes;
    }

    public void setFoundGenes(List<FoundGeneEntity> foundGenes) {
        this.foundGenes = foundGenes;
    }
}
