package com.practica.practicaRest.controllers;

import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public AddressDto createAditionalAddress(@RequestBody AddressDto addressDto){
        addressDto.setPrincipal(false);
        return addressService.newAddress(addressDto);
    }
    @GetMapping("/{id}")
    public List<AddressDto> getAddressesByCustomer(@PathVariable Long costumerId){
        return addressService.searchByCustomer(costumerId);
    }
}
