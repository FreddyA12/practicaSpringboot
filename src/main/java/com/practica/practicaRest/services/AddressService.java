package com.practica.practicaRest.services;

import com.practica.practicaRest.dtos.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto newAddress( AddressDto addressDto);
    List<AddressDto> searchByCustomer(Long customerId);
    void deleteAddress(Long AddressId);
    AddressDto searchPrincipalAddress(Long customerId);
}
