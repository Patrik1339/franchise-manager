package github.patrik1339.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "addresses")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public Address(Long id, String country, String county, String city, String street, String streetNumber) {
        this.id = id;
        this.country = country;
        this.county = county;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }
}