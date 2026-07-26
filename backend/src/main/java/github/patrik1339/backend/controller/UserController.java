package github.patrik1339.backend.controller;

import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{email}")
    public List<UserDTO> findUsersByEmail(@PathVariable String email) {
        return userService.findUsersByEmail(email);
    }
}