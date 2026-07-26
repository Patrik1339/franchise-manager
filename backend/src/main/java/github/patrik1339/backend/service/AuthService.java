package github.patrik1339.backend.service;

import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.enums.SystemRole;
import github.patrik1339.backend.model.User;
import github.patrik1339.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserDTO register(String email, String rawPassword) {
        if (userRepository.findUserByEmail(email) != null) {
            return null;
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, SystemRole.NORMAL_USER, new HashSet<>(), email, hashedPassword);

        user = userRepository.save(user);

        return UserDTO.builder()
                .id(user.getId())
                .systemRole(user.getSystemRole())
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public UserDTO login(String email, String rawPassword) {
        User user = userRepository.findUserByEmail(email);

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .systemRole(user.getSystemRole())
                .email(user.getEmail())
                .build();
    }
}