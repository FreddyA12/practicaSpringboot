package com.practica.practicaRest.services;

import com.practica.practicaRest.dtos.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto newAddress( AddressDto addressDto);
    List<AddressDto> searchByCustomer(Long customerId);

    AddressDto searchPrincipalAddress(Long customerId);
}
