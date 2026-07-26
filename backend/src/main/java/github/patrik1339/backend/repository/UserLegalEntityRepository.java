package github.patrik1339.backend.repository;

import github.patrik1339.backend.model.UserLegalEntity;
import java.util.List;

public interface UserLegalEntityRepository {
    UserLegalEntity save(UserLegalEntity userLegalEntity);
    List<UserLegalEntity> findByUserId(Long userId);
}
