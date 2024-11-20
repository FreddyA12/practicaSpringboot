package com.practica.practicaRest.controllers;

import com.practica.practicaRest.presenters.CustomerPresenter;
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
    public List<CustomerPresenter> getCustomersByIdOrName(@RequestParam (required = false) String identificationNumber,
                                                          @RequestParam (required = false)  String name){
        return customerService.searchCustomers(identificationNumber,name);
    }

    @PostMapping
    public CustomerPresenter saveCustomer(@RequestBody CustomerPresenter customerPresenter){
        return customerService.saveCustomer(customerPresenter);
    }

    @PutMapping
    public CustomerPresenter editCustomer(@RequestBody CustomerPresenter customerPresenter){
        return customerService.editCustomer(customerPresenter);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
