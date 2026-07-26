package github.patrik1339.backend.repository;

import github.patrik1339.backend.exceptions.RepositoryException;
import github.patrik1339.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserHibernateRepository implements UserRepository {
    private final Logger logger = LogManager.getLogger(UserHibernateRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByEmail(String email) {
        return entityManager.createQuery("FROM User WHERE email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(User user) {
        try {
            entityManager.persist(user);
            return user;
        } catch (Exception ex) {
            logger.error("Error saving user", ex);
            throw new RepositoryException("Error saving user", ex);
        }
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return entityManager.createQuery("FROM User WHERE email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }
}