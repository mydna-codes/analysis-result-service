package codes.mydna.analysis_result.mappers;

import codes.mydna.analysis_result.entities.AnalysisResultEntity;
import codes.mydna.analysis_result.lib.AnalysisResult;
import codes.mydna.analysis_result.lib.AnalysisResultSummary;

import java.util.stream.Collectors;

public class AnalysisResultMapper {

    public static AnalysisResultSummary fromEntityToSummary(AnalysisResultEntity entity){

        if(entity == null)
            return null;

        AnalysisResultSummary summary = new AnalysisResultSummary();
        BaseMapper.fromEntity(entity, summary);
        summary.setAnalysisName(entity.getAnalysisName());
        summary.setAnalysisExecutionTime(entity.getAnalysisExecutionTime());
        summary.setTotalExecutionTime(entity.getTotalExecutionTime());
        summary.setStatus(entity.getStatus());
        return summary;
    }

    public static AnalysisResult fromEntity(AnalysisResultEntity entity){

        if (entity == null)
            return null;

        AnalysisResult result = new AnalysisResult();
        BaseMapper.fromEntity(entity, result);
        result.setAnalysisName(entity.getAnalysisName());
        result.setAnalysisExecutionTime(entity.getAnalysisExecutionTime());
        result.setTotalExecutionTime(entity.getTotalExecutionTime());
        result.setStatus(entity.getStatus());
        result.setEnzymes(entity.getFoundEnzymes()
                .stream()
                .map(FoundEnzymeMapper::fromEntity)
                .collect(Collectors.toList()));
        result.setGenes(entity.getFoundGenes()
                .stream()
                .map(FoundGeneMapper::fromEntity)
                .collect(Collectors.toList()));
        return result;
    }

    public static AnalysisResultEntity toEntity(AnalysisResult result){

        if(result == null)
            return null;

        AnalysisResultEntity entity = new AnalysisResultEntity();
        entity.setAnalysisName(result.getAnalysisName());
        entity.setTotalExecutionTime(result.getTotalExecutionTime());
        entity.setAnalysisExecutionTime(result.getAnalysisExecutionTime());
        entity.setDnaId(result.getDna().getId());
        entity.setStatus(result.getStatus());
        entity.setFoundEnzymes(result.getEnzymes()
                .stream()
                .map(FoundEnzymeMapper::toEntity)
                .collect(Collectors.toList()));
        entity.setFoundGenes(result.getGenes()
                .stream()
                .map(FoundGeneMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }

}
