package github.patrik1339.backend.service;

import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.model.User;
import github.patrik1339.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDTO login(String email, String rawPassword) {
        User user = userRepository.findUserByEmail(email);

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return new UserDTO(user.getId(), user.getRole(), user.getEmail());
    }
}