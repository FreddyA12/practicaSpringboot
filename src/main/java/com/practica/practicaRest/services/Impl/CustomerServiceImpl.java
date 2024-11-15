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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        //Verificar que no tenga el mismo numero
        List<Customer> customers = customerRepository.findByIdentificationNumber(customerDto.getIdentificationNumber());
        if (!customers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Identification Number already exists");
        }else {

            Customer customer =dtoToCustomer(customerDto);
            //Asigna la direccion al cliente
            Address address = Address.builder()
                    .principal(true)
                    .address(customerDto.getMainAddress())
                    .customer(customer)
                    .province(customerDto.getMainProvince())
                    .city(customerDto.getMainCity()).build();
            customer.getListAddresses().add(address);
            //Guardar el cliente
            customer = customerRepository.save(customer);

            //Asignar la drieccion principal y retornar
            CustomerDto customerDto1 = customerToDto(customer);
            customerDto1.setMainAddress(address.getAddress());
            customerDto1.setMainCity(address.getCity());
            customerDto1.setMainProvince(address.getProvince());
            return customerDto1;
        }
    }

    @Override
    public CustomerDto editCustomer(CustomerDto customerDto) {
        //Verificar que tenga un id
        if (customerDto.getId() == null){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Send an id");
        }
        //Obtener el customer
        Customer customer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Customer doesn't exist"));

        //Verificar que no tenga el mismo numero solo si se va a editar eso
        if (customerDto.getIdentificationNumber()!=null){
            List<Customer> customers = customerRepository.findByIdentificationNumber(customerDto.getIdentificationNumber());
            if (!customers.isEmpty() ) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Identification Number already exists");
            }
            customer.setIdentificationNumber(customerDto.getIdentificationNumber());
        }
            //Compara campos vacios y asigna
        if (customerDto.getNames()!=null){
            customer.setNames(customerDto.getNames());
        }
        if (customerDto.getIdentificationType()!=null){
            customer.setIdentificationType(customerDto.getIdentificationType());
        }
        if (customerDto.getPhoneNumber()!=null){
            customer.setPhoneNumber(customerDto.getPhoneNumber());
        }
        if (customerDto.getEmail()!=null){
            customer.setEmail(customerDto.getEmail());
        }

        //Edita el cliente
        customer = customerRepository.save(customer);

        //Dto para devolver el cliente
        //Asignar la drieccion principal y retornar
        AddressDto addressDto = addressService.searchPrincipalAddress(customer.getId());
        CustomerDto customerDto1 = customerToDto(customer);
        customerDto1.setMainAddress(addressDto.getAddress());
        customerDto1.setMainCity(addressDto.getCity());
        customerDto1.setMainProvince(addressDto.getProvince());
        return customerDto1;


    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Customer doesn't exist"));
        customerRepository.delete(customer);
    }

    private CustomerDto customerToDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
    private Customer dtoToCustomer(CustomerDto customerDto){
        return this.modelMapper.map(customerDto, Customer.class);
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
