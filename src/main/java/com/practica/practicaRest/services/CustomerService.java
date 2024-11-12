package com.practica.practicaRest.services;

import com.practica.practicaRest.dtos.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {
    List<CustomerDto> searchCustomers(String identificationNumber, String name);
}
