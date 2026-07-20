package github.patrik1339.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "legal_entities")
public class LegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String taxIdentificationNumber; // CUI

    @Column(nullable = false)
    private String tradeRegistryNumber; // J/RegCom

    @Column(nullable = false)
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate establishmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address officeAddress;

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserLegalEntity> members = new HashSet<>();

    private boolean isActive = true;
}