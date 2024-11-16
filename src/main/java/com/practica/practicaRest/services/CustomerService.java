package com.practica.practicaRest.services;

import com.practica.practicaRest.entities.Customer;
import com.practica.practicaRest.presenters.CustomerPresenter;

import java.util.List;


public interface CustomerService {
    List<CustomerPresenter> searchCustomers(String identificationNumber, String name);
    CustomerPresenter saveCustomer(CustomerPresenter customerPresenter);
    CustomerPresenter editCustomer(CustomerPresenter customerPresenter);
    Customer getCustomerById(Long id);
    void deleteCustomer(Long id);
}
