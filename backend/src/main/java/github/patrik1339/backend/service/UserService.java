package github.patrik1339.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.patrik1339.backend.dto.DTOUtils;
import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.exceptions.ServiceException;
import github.patrik1339.backend.model.User;
import github.patrik1339.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public List<UserDTO> findUsersByEmail(String email) {
        String cacheKey = email.toLowerCase().trim();

        String cachedData = redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, new TypeReference<>() {});
            } catch (JsonProcessingException ex) {
                throw new ServiceException("Error processing json", ex);
            }
        }

        String sqlQueryParam = "%" + email + "%";
        List<User> users = userRepository.findUsersByEmail(sqlQueryParam);
        List<UserDTO> userDTOS = DTOUtils.toDTO(users);

        try {
            String jsonToCache = objectMapper.writeValueAsString(userDTOS);
            redisTemplate.opsForValue().set(cacheKey, jsonToCache, Duration.ofMinutes(5));
        } catch (JsonProcessingException ex) {
            throw new ServiceException("Error processing json", ex);
        }

        return userDTOS;
    }
}