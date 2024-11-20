package com.practica.practicaRest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table (name = "addresses")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean principal;
    private String province;
    private String city;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id" )
    private Customer customer;
}
