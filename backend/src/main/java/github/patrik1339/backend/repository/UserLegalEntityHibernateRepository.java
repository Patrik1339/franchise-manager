package github.patrik1339.backend.repository;

import github.patrik1339.backend.exceptions.RepositoryException;
import github.patrik1339.backend.model.UserLegalEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserLegalEntityHibernateRepository implements UserLegalEntityRepository {
    private final Logger logger = LogManager.getLogger(UserLegalEntityHibernateRepository.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserLegalEntity save(UserLegalEntity userLegalEntity) {
        try {
            entityManager.persist(userLegalEntity);
            return userLegalEntity;
        } catch (Exception ex) {
            logger.error("Error saving userLegalEntity", ex);
            throw new RepositoryException("Error saving userLegalEntity", ex);
        }
    }

    @Override
    public List<UserLegalEntity> findByUserId(Long userId) {
        return entityManager.createQuery("FROM UserLegalEntity ule WHERE ule.user.id = :userId", UserLegalEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
