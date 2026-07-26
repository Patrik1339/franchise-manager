package github.patrik1339.backend.dto;

import github.patrik1339.backend.enums.ResponseType;
import github.patrik1339.backend.model.LegalEntity;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private ResponseType responseType;
    private UserDTO userDTO;
    private String token;
    private List<LegalEntity> franchises;
    private LegalEntityDTO legalEntityDTO;
}
