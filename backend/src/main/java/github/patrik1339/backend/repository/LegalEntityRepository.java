package github.patrik1339.backend.repository;

import github.patrik1339.backend.model.LegalEntity;
import java.util.List;

public interface LegalEntityRepository {
    List<LegalEntity> findFranchisesForFranchisor(Long franchisorId);
    LegalEntity findFranchiseById(Long franchiseId);
    LegalEntity update(LegalEntity franchise);
    LegalEntity save(LegalEntity franchise);
}
