package github.patrik1339.backend.dto;

import github.patrik1339.backend.enums.BusinessRole;
import github.patrik1339.backend.enums.RequestType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private RequestType requestType;
    private UserDTO userDTO;
    private LegalEntityDTO legalEntityDTO;
    private Long franchisorId;
    private BusinessRole businessRole;
}