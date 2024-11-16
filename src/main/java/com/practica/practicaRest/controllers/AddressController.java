package com.practica.practicaRest.controllers;

import com.practica.practicaRest.presenters.AddressPresenter;
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
    public AddressPresenter createAditionalAddress(@RequestBody AddressPresenter addressPresenter){
        addressPresenter.setPrincipal(false);
        return addressService.createAddress(addressPresenter);
    }
    @GetMapping("/{id}")
    public List<AddressPresenter> getAddressesByCustomer(@PathVariable Long costumerId){
        return addressService.searchByCustomer(costumerId);
    }
}
