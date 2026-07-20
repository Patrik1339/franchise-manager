package github.patrik1339.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String county;
    private String city;
    private String street;
    private String streetNumber;
    private String block;
    private String staircase;
    private String floor;
    private String apartmentNumber;
}