package com.practica.practicaRest.controllers;

import com.practica.practicaRest.dtos.CustomerDto;
import com.practica.practicaRest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getCustomersByIdOrName(@RequestParam (required = false) String identificationNumber,
                                                    @RequestParam (required = false)  String name){
        return customerService.searchCustomers(identificationNumber,name);
    }

    @PostMapping
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return customerService.saveCustomer(customerDto);
    }
}
