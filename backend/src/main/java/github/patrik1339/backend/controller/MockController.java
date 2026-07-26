package github.patrik1339.backend.controller;

import github.patrik1339.backend.model.Address;
import github.patrik1339.backend.model.LegalEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/mock")
public class MockController {

    private static final List<LegalEntity> franchises = new ArrayList<>();
    private static long nextId = 2L;

    static {
        Address address = new Address(1L, "Romania", "Bucuresti", "Bucuresti", "Mihai Viteazul", "27");
        LegalEntity mockEntity = new LegalEntity(
                1L,
                "RO6205722",
                "J40/16755/1994",
                "McDonald's Romania",
                "mcromania@mc.com",
                "0757772184",
                LocalDate.of(1994, 1, 20),
                address,
                null,
                new HashSet<>(),
                new HashSet<>(),
                true
        );
        franchises.add(mockEntity);
    }

    @GetMapping("/franchises")
    public ResponseEntity<List<LegalEntity>> getMockFranchises() {
        return ResponseEntity.ok(franchises);
    }

    @PostMapping("/franchises")
    public ResponseEntity<LegalEntity> createMockFranchise(@RequestBody LegalEntity newEntity) {
        newEntity.setId(nextId++);
        franchises.add(newEntity);
        return ResponseEntity.ok(newEntity);
    }

    @PutMapping("/franchises/{id}")
    public ResponseEntity<LegalEntity> updateMockFranchise(@PathVariable Long id, @RequestBody LegalEntity updatedEntity) {
        for (int i = 0; i < franchises.size(); i++) {
            if (franchises.get(i).getId().equals(id)) {
                updatedEntity.setId(id);
                franchises.set(i, updatedEntity);
                return ResponseEntity.ok(updatedEntity);
            }
        }
        return ResponseEntity.notFound().build();
    }
}