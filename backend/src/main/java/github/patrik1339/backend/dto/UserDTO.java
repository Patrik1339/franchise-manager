package github.patrik1339.backend.dto;

import github.patrik1339.backend.enums.SystemRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private SystemRole systemRole;
    private String email;
    private String password;
}