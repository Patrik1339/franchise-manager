package github.patrik1339.backend.dto;

import github.patrik1339.backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private UserRole userRole;
    private String email;
}