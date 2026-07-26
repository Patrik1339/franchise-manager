package github.patrik1339.backend.repository;

import github.patrik1339.backend.model.LegalEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LegalEntityHibernateRepository implements LegalEntityRepository {
    private final Logger logger = LogManager.getLogger(LegalEntityHibernateRepository.class);

    @PersistenceContext
    private final EntityManager entityManager;

    public LegalEntityHibernateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<LegalEntity> findFranchisesForFranchisor(Long franchisorId) {
        return entityManager.createQuery("FROM LegalEntity WHERE franchisor.id = :franchisorId", LegalEntity.class)
                .setParameter("franchisorId", franchisorId)
                .getResultList();
    }

    @Override
    public LegalEntity findFranchiseById(Long franchiseId) {
        return entityManager.find(LegalEntity.class, franchiseId);
    }

    @Override
    public LegalEntity update(LegalEntity franchise) {
        logger.info("Updating franchise: {}", franchise);
        entityManager.merge(franchise);
        logger.info("Franchise updates successfully: {}", franchise);
        return franchise;
    }

    @Override
    public LegalEntity save(LegalEntity franchise) {
        logger.info("Saving franchise: {}", franchise);
        entityManager.persist(franchise);
        logger.info("Franchise saved successfully, id: {}", franchise);
        return franchise;
    }
}