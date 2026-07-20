package github.patrik1339.backend.model;

import github.patrik1339.backend.enums.BusinessRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_legal_entities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "legal_entity_id"})
})
public class UserLegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_role")
    private BusinessRole businessRole;
}