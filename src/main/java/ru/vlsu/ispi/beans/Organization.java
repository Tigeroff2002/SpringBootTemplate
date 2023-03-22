package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "Organizations")
@AllArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String city;

    private String site;

    public Organization() {

    }
    public Organization(String caption, String city, String site){
        this.caption = caption;
        this.city = city;
        this.site = site;
    }
}