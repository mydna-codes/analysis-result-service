package codes.mydna.services.impl;

import codes.mydna.auth.common.models.User;
import codes.mydna.clients.grpc.DnaServiceGrpcClient;
import codes.mydna.clients.grpc.EnzymeServiceGrpcClient;
import codes.mydna.clients.grpc.GeneServiceGrpcClient;
import codes.mydna.clients.grpc.models.CheckedEntity;
import codes.mydna.entities.AnalysisResultEntity;
import codes.mydna.entities.FoundEnzymeEntity;
import codes.mydna.entities.FoundGeneEntity;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.*;
import codes.mydna.lib.enums.Status;
import codes.mydna.mappers.AnalysisResultMapper;
import codes.mydna.mappers.BaseMapper;
import codes.mydna.services.AnalysisResultService;
import codes.mydna.utils.EntityList;
import codes.mydna.validation.Assert;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnalysisResultServiceImpl implements AnalysisResultService {

    @Inject
    private EntityManager em;

    @Inject
    private DnaServiceGrpcClient dnaServiceGrpcClient;

    @Inject
    private EnzymeServiceGrpcClient enzymeServiceGrpcClient;

    @Inject
    private GeneServiceGrpcClient geneServiceGrpcClient;

    @Override
    public EntityList<AnalysisResultSummary> getAnalysisResultSummaries(QueryParameters qp, User user) {
        List<AnalysisResultSummary> results = JPAUtils.queryEntities(em, AnalysisResultEntity.class, qp)
                .stream()
                .map(AnalysisResultMapper::fromEntityToSummary)
                .collect(Collectors.toList());
        Long count = JPAUtils.queryEntitiesCount(em, AnalysisResultEntity.class, qp);
        return new EntityList<>(results, count);
    }

    @Override
    public AnalysisResult getAnalysisResult(String id, User user) {

        Assert.userNotNull(user);
        Assert.fieldNotNull(id, "id");

        AnalysisResultEntity entity = getAnalysisResultEntity(id, user);
        if (entity == null)
            throw new NotFoundException(AnalysisResult.class, id);

        CheckedEntity<Dna> receivedDna = dnaServiceGrpcClient.getDna(entity.getDnaId(), user);
        if (receivedDna.getStatus() != Status.OK) {
            // If analysis DNA has been removed, remove analysis
            if (receivedDna.getStatus() == Status.ENTITY_NOT_FOUND) {
                removeAnalysisResult(id, user);
            }
            return null;
        }

        AnalysisResult result = AnalysisResultMapper.fromEntity(entity);

        // Populate dna
        result.setDna(receivedDna.getEntity());

        // Populate enzymes
        List<Enzyme> enzymes = enzymeServiceGrpcClient.getMultipleEnzymes(entity.getFoundEnzymes()
                .stream()
                .map(FoundEnzymeEntity::getEnzymeId)
                .collect(Collectors.toList()
                ), user);

        for (Enzyme e : enzymes) {
            for (FoundEnzyme fe : result.getEnzymes()) {
                if (fe.getEnzyme().getId().equals(e.getId()))
                    fe.getEnzyme().setSequence(e.getSequence());
            }
        }

        // Populate genes
        List<Gene> genes = geneServiceGrpcClient.getMultipleGenes(entity.getFoundGenes()
                .stream()
                .map(FoundGeneEntity::getGeneId)
                .collect(Collectors.toList()
                ), user);

        for (Gene g : genes) {
            for (FoundGene fg : result.getGenes()) {
                if (fg.getGene().getId().equals(g.getId()))
                    fg.getGene().setSequence(g.getSequence());
            }
        }
        return result;
    }

    @Override
    public AnalysisResult insertAnalysisResult(AnalysisResult result, User user) {

        Assert.userNotNull(user);
        Assert.objectNotNull(result, AnalysisResult.class);
        Assert.objectNotNull(result.getDna(), Dna.class);

        AnalysisResultEntity entity = AnalysisResultMapper.toEntity(result);
        entity.setOwnerId(user.getId());

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        BaseMapper.fromEntity(entity, result);
        return result;
    }

    @Override
    public boolean removeAnalysisResult(String id, User user) {

        Assert.userNotNull(user);

        AnalysisResultEntity entity = getAnalysisResultEntity(id, user);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    private AnalysisResultEntity getAnalysisResultEntity(String id, User user) {
        try {
            return (AnalysisResultEntity) em.createNamedQuery(AnalysisResultEntity.GET_BY_ID_AND_OWNER_ID)
                    .setParameter("id", id)
                    .setParameter("owner_id", user.getId())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

}
