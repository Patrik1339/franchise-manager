package github.patrik1339.backend.service;

import github.patrik1339.backend.dto.DTOUtils;
import github.patrik1339.backend.dto.LegalEntityDTO;
import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.enums.BusinessRole;
import github.patrik1339.backend.exceptions.ServiceException;
import github.patrik1339.backend.model.LegalEntity;
import github.patrik1339.backend.model.User;
import github.patrik1339.backend.repository.LegalEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import github.patrik1339.backend.model.UserLegalEntity;
import github.patrik1339.backend.repository.UserRepository;
import github.patrik1339.backend.repository.UserLegalEntityRepository;

@Service
@RequiredArgsConstructor
public class LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final UserRepository userRepository;
    private final UserLegalEntityRepository userLegalEntityRepository;

    public List<LegalEntity> findFranchisesForFranchisor(Long franchisorId) {
        return legalEntityRepository.findFranchisesForFranchisor(franchisorId);
    }

    public void validateLegalEntityDTO(LegalEntityDTO legalEntityDTO) {
        if (legalEntityDTO.getTaxIdentificationNumber() == null ||
                legalEntityDTO.getTaxIdentificationNumber().trim().isEmpty() ||
                legalEntityDTO.getName() == null ||
                legalEntityDTO.getName().trim().isEmpty() ||
                legalEntityDTO.getTradeRegistryNumber() == null ||
                legalEntityDTO.getTradeRegistryNumber().trim().isEmpty()) {
            throw new ServiceException("Missing required fields (Name, Tax Identification Number, Trade Registry Number)");
        }
    }

    @Transactional
    public LegalEntityDTO createLegalEntity(UserDTO userDTO, LegalEntityDTO legalEntityDTO) {
        if (userDTO == null || userDTO.getId() == null) {
            throw new ServiceException("User ID cannot be null!");
        }

        User user = userRepository.findUserById(userDTO.getId());
        if (user == null) {
            throw new ServiceException("No user found with id: " + userDTO.getId());
        }

        validateLegalEntityDTO(legalEntityDTO);

        LegalEntity legalEntity = DTOUtils.fromDTO(legalEntityDTO);
        legalEntity.setFranchisor(null);

        legalEntityRepository.save(legalEntity);

        UserLegalEntity userLegalEntity = new UserLegalEntity();
        userLegalEntity.setUser(user);
        userLegalEntity.setLegalEntity(legalEntity);
        userLegalEntity.setBusinessRole(BusinessRole.FRANCHISOR);
        
        userLegalEntityRepository.save(userLegalEntity);

        return DTOUtils.toDTO(legalEntity);
    }

    @Transactional
    public LegalEntityDTO updateFranchise(LegalEntityDTO legalEntityDTO) {
        if (legalEntityDTO.getId() == null) {
            throw new ServiceException("Franchise ID cannot be null for update!");
        }

        LegalEntity franchise = legalEntityRepository.findFranchiseById(legalEntityDTO.getId());

        if (franchise == null) {
            throw new ServiceException("Franchise not found with ID: " + legalEntityDTO.getId());
        }

        DTOUtils.updateEntityFromDTO(franchise, legalEntityDTO);

        franchise = legalEntityRepository.update(franchise);
        return DTOUtils.toDTO(franchise);
    }

    public List<LegalEntity> getMyLegalEntities(Long userId) {
        List<UserLegalEntity> connections = userLegalEntityRepository.findByUserId(userId);
        return connections.stream()
                .map(UserLegalEntity::getLegalEntity)
                .toList();
    }

    @Transactional
    public LegalEntityDTO createFranchise(UserDTO userDTO, Long franchisorId, LegalEntityDTO legalEntityDTO) {
        if (userDTO == null || userDTO.getId() == null) {
            throw new ServiceException("User ID cannot be null!");
        }

        User user = userRepository.findUserById(userDTO.getId());
        if (user == null) {
            throw new ServiceException("No user found with id: " + userDTO.getId());
        }

        if (franchisorId == null) {
            throw new ServiceException("Franchisor ID cannot be null!");
        }

        LegalEntity franchisor = legalEntityRepository.findFranchiseById(franchisorId);
        if (franchisor == null) {
            throw new ServiceException("Franchisor not found with ID: " + franchisorId);
        }

        if (legalEntityDTO.getTaxIdentificationNumber() == null || legalEntityDTO.getTaxIdentificationNumber().trim().isEmpty() ||
            legalEntityDTO.getName() == null || legalEntityDTO.getName().trim().isEmpty() ||
            legalEntityDTO.getTradeRegistryNumber() == null || legalEntityDTO.getTradeRegistryNumber().trim().isEmpty()) {
            throw new ServiceException("Required fields (Name, Tax Identification Number, Trade Registry Number) cannot be empty!");
        }

        LegalEntity franchise = DTOUtils.fromDTO(legalEntityDTO);
        franchise.setFranchisor(franchisor);

        legalEntityRepository.save(franchise);

        UserLegalEntity userLegalEntity = new UserLegalEntity();
        userLegalEntity.setUser(user);
        userLegalEntity.setLegalEntity(franchise);
        userLegalEntity.setBusinessRole(BusinessRole.FRANCHISOR);
        
        userLegalEntityRepository.save(userLegalEntity);

        return DTOUtils.toDTO(franchise);
    }

    @Transactional
    public void addUserAssociate(Long franchiseId, UserDTO userDTO, BusinessRole businessRole) {
        Long userId = userDTO.getId();
        if (userId == null) {
            throw new ServiceException("User ID cannot be null!");
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ServiceException("No user found with id: " + userId);
        }

        LegalEntity franchise = legalEntityRepository.findFranchiseById(franchiseId);
        if (franchise == null) {
            throw new ServiceException("Franchise not found with ID: " + franchiseId);
        }

        UserLegalEntity userLegalEntity = new UserLegalEntity();
        userLegalEntity.setUser(user);
        userLegalEntity.setLegalEntity(franchise);
        userLegalEntity.setBusinessRole(businessRole);

        userLegalEntityRepository.save(userLegalEntity);
    }
}