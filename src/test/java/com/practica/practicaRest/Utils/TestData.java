package com.practica.practicaRest.Utils;

import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.dtos.CustomerDto;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.entities.Customer;

public class TestData {
    private static final TestData INSTANCE = new TestData();
    public static TestData getInstance(){return INSTANCE;};

    public Customer customer(){
        return Customer.builder().id(1L).names("Freddy Alvarez")
                .email("fas@asd.c")
                .identificationType("Cedula")
                .identificationNumber("111")
                .phoneNumber("099")
        .build();
    }

    public CustomerDto customerDto(){
        return CustomerDto.builder()
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

    public AddressDto addressDto(){
        return AddressDto.builder()
                .id(1L)
                .province("Tungurahua")
                .city("Ambato")
                .address("Nada")
                .principal(false)
                .customerId(1L)
                .build();
    }
}
