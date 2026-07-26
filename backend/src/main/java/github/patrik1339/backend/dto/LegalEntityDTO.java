package github.patrik1339.backend.dto;

import github.patrik1339.backend.model.Address;
import github.patrik1339.backend.model.LegalEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityDTO {
    private Long id;
    private String taxIdentificationNumber;
    private String tradeRegistryNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate establishmentDate;
    private Address address;
    private LegalEntity franchisor;
    private Boolean active;
}
