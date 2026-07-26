package github.patrik1339.backend.dto;

import github.patrik1339.backend.model.LegalEntity;
import github.patrik1339.backend.model.User;
import java.util.HashSet;
import java.util.List;

public class DTOUtils {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(DTOUtils::toDTO)
                .toList();
    }

    public static LegalEntityDTO toDTO(LegalEntity franchise) {
        return LegalEntityDTO.builder()
                .id(franchise.getId())
                .taxIdentificationNumber(franchise.getTaxIdentificationNumber())
                .tradeRegistryNumber(franchise.getTradeRegistryNumber())
                .name(franchise.getName())
                .email(franchise.getEmail())
                .phoneNumber(franchise.getPhoneNumber())
                .establishmentDate(franchise.getEstablishmentDate())
                .address(franchise.getAddress())
                .active(franchise.isActive())
                .build();
    }

    public static LegalEntity fromDTO(LegalEntityDTO legalEntityDTO) {
        return new LegalEntity(null,
                legalEntityDTO.getTaxIdentificationNumber(),
                legalEntityDTO.getTradeRegistryNumber(),
                legalEntityDTO.getName(),
                legalEntityDTO.getEmail(),
                legalEntityDTO.getPhoneNumber(),
                legalEntityDTO.getEstablishmentDate(),
                legalEntityDTO.getAddress(),
                legalEntityDTO.getFranchisor(),
                new HashSet<>(),
                new HashSet<>(),
                legalEntityDTO.getActive() != null ? legalEntityDTO.getActive() : true);
    }

    public static void updateEntityFromDTO(LegalEntity franchise, LegalEntityDTO legalEntityDTO) {
        franchise.setTaxIdentificationNumber(legalEntityDTO.getTaxIdentificationNumber());
        franchise.setTradeRegistryNumber(legalEntityDTO.getTradeRegistryNumber());
        franchise.setName(legalEntityDTO.getName());
        franchise.setEmail(legalEntityDTO.getEmail());
        franchise.setPhoneNumber(legalEntityDTO.getPhoneNumber());
        franchise.setEstablishmentDate(legalEntityDTO.getEstablishmentDate());
        franchise.setAddress(legalEntityDTO.getAddress());
        if (legalEntityDTO.getActive() != null) {
            franchise.setActive(legalEntityDTO.getActive());
        }
    }
}