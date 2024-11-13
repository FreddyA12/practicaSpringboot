package com.practica.practicaRest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "customers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "identification_type")
    private String identificationType;
    @Column(name = "identification_number")
    private String identificationNumber;
    @Column(name = "names")
    private String names;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "customer", orphanRemoval = true ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> listAddresses = new ArrayList<>();
}
