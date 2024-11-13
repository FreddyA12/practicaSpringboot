package com.practica.practicaRest.dtos;

import com.practica.practicaRest.entities.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private boolean principal;
    private String province;
    private String city;
    private String address;
    private Long customerId;

}
