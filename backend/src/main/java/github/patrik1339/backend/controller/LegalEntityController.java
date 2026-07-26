package github.patrik1339.backend.controller;

import github.patrik1339.backend.dto.LegalEntityDTO;
import github.patrik1339.backend.dto.Request;
import github.patrik1339.backend.dto.Response;
import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.enums.BusinessRole;
import github.patrik1339.backend.enums.ResponseType;
import github.patrik1339.backend.exceptions.ServiceException;
import github.patrik1339.backend.model.LegalEntity;
import github.patrik1339.backend.service.LegalEntityService;
import github.patrik1339.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/legal_entity")
public class LegalEntityController {
    private static final Logger log = LoggerFactory.getLogger(LegalEntityController.class);
    private final LegalEntityService legalEntityService;
    private final UserService userService;

    @GetMapping("/franchises/{franchisorId}")
    public ResponseEntity<Response> findFranchisesForFranchisor(@PathVariable Long franchisorId) {
        List<LegalEntity> franchises = legalEntityService.findFranchisesForFranchisor(franchisorId);
        Response response = Response.builder()
                .responseType(ResponseType.OK)
                .franchises(franchises)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response); // 200 OK
    }

    @GetMapping("/my-entities/{userId}")
    public ResponseEntity<Response> getMyLegalEntities(@PathVariable Long userId) {
        List<LegalEntity> entities = legalEntityService.getMyLegalEntities(userId);
        Response response = Response.builder()
                .responseType(ResponseType.OK)
                .franchises(entities) // reusing franchises field for simplicity
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/franchises/{franchiseId}")
    public ResponseEntity<Response> updateFranchise(@PathVariable Long franchiseId, @RequestBody Request request) {
        LegalEntityDTO legalEntityDTO = request.getLegalEntityDTO();
        legalEntityDTO = legalEntityService.updateFranchise(legalEntityDTO);

        if (legalEntityDTO == null) {
            Response response = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Response response = Response.builder()
                .responseType(ResponseType.OK)
                .legalEntityDTO(legalEntityDTO)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createLegalEntity(@RequestBody Request createLegalEntityRequest) {
        UserDTO userDTO = createLegalEntityRequest.getUserDTO();
        LegalEntityDTO legalEntityDTO = createLegalEntityRequest.getLegalEntityDTO();

        try {
            LegalEntityDTO createdLegalEntityDTO = legalEntityService.createLegalEntity(userDTO, legalEntityDTO);

            Response response = Response.builder()
                    .responseType(ResponseType.OK)
                    .legalEntityDTO(createdLegalEntityDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            log.error("Error creating legal entity", ex);
            Response response = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(value = "/franchises/create")
    public ResponseEntity<Response> createFranchise(@RequestBody Request createFranchiseRequest) {
        Long franchisorId = createFranchiseRequest.getFranchisorId();
        LegalEntityDTO legalEntityDTO = createFranchiseRequest.getLegalEntityDTO();
        UserDTO userDTO = createFranchiseRequest.getUserDTO();

        try {
            LegalEntityDTO createdLegalEntityDTO = legalEntityService.createFranchise(userDTO, franchisorId, legalEntityDTO);

            Response response = Response.builder()
                    .responseType(ResponseType.OK)
                    .legalEntityDTO(createdLegalEntityDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            Response response = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(value = "/franchises/{franchiseId}/users")
    public ResponseEntity<Response> addUserAsociate(@PathVariable Long franchiseId, @RequestBody Request request) {
        UserDTO userDTO = request.getUserDTO();
        BusinessRole businessRole = request.getBusinessRole();

        try {
            legalEntityService.addUserAssociate(franchiseId, userDTO, businessRole);
        } catch (ServiceException ex) {
            Response response = Response.builder()
                    .responseType(ResponseType.ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Response response = Response.builder()
                .responseType(ResponseType.OK)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}