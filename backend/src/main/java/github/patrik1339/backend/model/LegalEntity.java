package github.patrik1339.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "legal_entities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchisor_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private LegalEntity franchisor;

    @OneToMany(mappedBy = "franchisor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<LegalEntity> franchises = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<UserLegalEntity> members = new HashSet<>();

    private boolean isActive = true;
}