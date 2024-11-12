package com.practica.practicaRest.services.Impl;

import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.dtos.CustomerDto;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.entities.Customer;
import com.practica.practicaRest.repositories.CustomerRepository;
import com.practica.practicaRest.services.AddressService;
import com.practica.practicaRest.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    @Lazy
    private AddressService addressService;

    private final ModelMapper modelMapper;

    public CustomerServiceImpl(){
        modelMapper = new ModelMapper();
    }

    @Override
    public List<CustomerDto> searchCustomers(String identificationNumber, String name) {
        List<Customer> customerList;
        if (identificationNumber == null){
            //find by name
            customerList = customerRepository.findByNamesContaining(name);
        }else{
            //find by identificationNumber
            customerList =  customerRepository.findByIdentificationNumberContaining(identificationNumber);
        }

        return customerList.stream().map(this::addPrincipalAddress).collect(Collectors.toList());

    }

    private CustomerDto customerToDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }

    private CustomerDto addPrincipalAddress(Customer customer){
        AddressDto address = addressService.searchPrincipalAddress(customer.getId());
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        customerDto.setMainCity(address.getCity());
        customerDto.setMainProvince(address.getProvince());
        customerDto.setMainAddress(address.getAddress());
        return customerDto;
    }
}
