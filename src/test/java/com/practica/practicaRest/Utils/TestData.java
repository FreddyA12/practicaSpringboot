package com.practica.practicaRest.Utils;

import com.practica.practicaRest.presenters.AddressPresenter;
import com.practica.practicaRest.presenters.CustomerPresenter;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.entities.Customer;

import java.util.List;

public class TestData {
    private static final TestData INSTANCE = new TestData();
    public static TestData getInstance(){return INSTANCE;};

    public Customer customer(){
        Address address = Address.builder()
                .id(1L)
                .province("Tungurahua")
                .city("Ambato")
                .address("Nada")
                .principal(true)
                .build();
        address.setPrincipal(true);
        Customer customer =  Customer.builder().id(1L).names("Freddy Alvarez")
                .email("fas@asd.c")
                .identificationType("Cedula")
                .identificationNumber("111")
                .phoneNumber("099")
                .listAddresses(List.of(address))
        .build();
        address.setCustomer(customer);
        return customer;
    }

    public CustomerPresenter customerDto(){
        return CustomerPresenter.builder()
                .id(1L).names("Freddy Alvarez")
                .email("fas@asd.c")
                .identificationType("Cedula")
                .identificationNumber("111")
                .phoneNumber("099")
                .mainCity("Ambato")
                .mainProvince("Tungurahua")
                .mainAddress("Nada")
                .build();
    }



    public Address address(){
        return Address.builder()
                .id(1L)
                .province("Tungurahua")
                .city("Ambato")
                .address("Nada")
                .principal(false)
                .customer(this.customer())
                .build();
    }

    public AddressPresenter addressDto(){
        return AddressPresenter.builder()
                .id(1L)
                .province("Tungurahua")
                .city("Ambato")
                .address("Nada")
                .principal(false)
                .customerId(1L)
                .build();
    }
}
