package com.practica.practicaRest.dtos;

import com.practica.practicaRest.entities.Address;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String email;
    private String phoneNumber;
    private String mainProvince;
    private String mainCity;
    private String mainAddress;

}
