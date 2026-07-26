package github.patrik1339.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWT filter processing: {} {}", request.getMethod(), request.getRequestURI());

        String token = getJwtFromHeader(request);
        String source = "header";
        if (token == null) {
            token = getJwtFromCookies(request);
            source = "cookie";
        }

        if (token == null) {
            log.warn("No JWT token found in header or cookie for: {} {}", request.getMethod(), request.getRequestURI());
        } else {
            log.debug("Token found via {}. Length={}, prefix={}", source, token.length(), token.substring(0, Math.min(10, token.length())));
            boolean valid = jwtUtil.isValid(token);
            log.debug("Token valid={}", valid);
            if (valid) {
                String email = jwtUtil.getUserEmailFromJwt(token);
                log.debug("Authenticated user: {}", email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("JWT token failed validation for: {} {}", request.getMethod(), request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt_token");
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}
