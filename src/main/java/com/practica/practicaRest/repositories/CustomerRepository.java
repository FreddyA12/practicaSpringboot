package com.practica.practicaRest.repositories;

import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    List<Customer> findByNamesContaining(String names);
    List<Customer> findByIdentificationNumberContaining(String identificationNumber);
}
