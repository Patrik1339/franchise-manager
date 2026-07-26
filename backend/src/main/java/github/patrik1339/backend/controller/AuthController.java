package github.patrik1339.backend.controller;

import github.patrik1339.backend.dto.Request;
import github.patrik1339.backend.dto.Response;
import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.enums.ResponseType;
import github.patrik1339.backend.security.JwtUtil;
import github.patrik1339.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/register")
    public ResponseEntity<Response> register(@RequestBody Request request) {
        UserDTO userDTO = authService.register(request.getUserDTO().getEmail(), request.getUserDTO().getPassword());

        if (userDTO == null) {
            Response errorResponse = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // 409 CONFLICT
        }

        Response successResponse = Response.builder()
                .responseType(ResponseType.OK)
                .userDTO(userDTO)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse); // 201 CREATED
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(@RequestBody Request request) {
        UserDTO userDTO = authService.login(request.getUserDTO().getEmail(), request.getUserDTO().getPassword());

        if (userDTO == null) {
            Response errorResponse = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // 401 UNAUTHORIZED
        }

        String token = jwtUtil.generateJwt(userDTO);

        ResponseCookie jwtCookie = ResponseCookie.from("jwt_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/franchise-manager")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        Response successResponse = Response.builder()
                .responseType(ResponseType.OK)
                .userDTO(userDTO)
                .token(token)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(successResponse); // 200 OK
    }
}