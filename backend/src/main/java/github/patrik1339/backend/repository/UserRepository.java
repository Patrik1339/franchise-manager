package github.patrik1339.backend.repository;

import github.patrik1339.backend.model.User;
import java.util.List;

public interface UserRepository {
    User findUserByEmail(String email);
    User save(User user);
    List<User> findUsersByEmail(String email);
    User findUserById(Long id);
}