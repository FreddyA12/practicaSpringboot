package com.practica.practicaRest.services.Impl;

import com.practica.practicaRest.presenters.AddressPresenter;
import com.practica.practicaRest.presenters.CustomerPresenter;
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
    public List<CustomerPresenter> searchCustomers(String identificationNumber, String name) {
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
    public CustomerPresenter saveCustomer(CustomerPresenter customerPresenter) {
        //Verificar que no tenga el mismo numero
        List<Customer> customers = customerRepository.findByIdentificationNumber(customerPresenter.getIdentificationNumber());
        if (!customers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Identification Number already exists");
        }else {

            Customer customer =presenterToCustomer(customerPresenter);
            //Asigna la direccion al cliente
            Address address = Address.builder()
                    .principal(true)
                    .address(customerPresenter.getMainAddress())
                    .customer(customer)
                    .province(customerPresenter.getMainProvince())
                    .city(customerPresenter.getMainCity()).build();
            customer.getListAddresses().add(address);
            //Guardar el cliente
            customer = customerRepository.save(customer);

            return customerToPresenter(customer);
        }
    }

    @Override
    public CustomerPresenter editCustomer(CustomerPresenter customerPresenter) {
        //Verificar que tenga un id
        if (customerPresenter.getId() == null){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Send an id");
        }
        //Obtener el customer
        Customer customer = customerRepository.findById(customerPresenter.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Customer doesn't exist"));

        //Verificar que no tenga el mismo numero solo si se va a editar eso
        if (customerPresenter.getIdentificationNumber()!=null){
            List<Customer> customers = customerRepository.findByIdentificationNumber(customerPresenter.getIdentificationNumber());
            if (!customers.isEmpty() ) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Identification Number already exists");
            }
            customer.setIdentificationNumber(customerPresenter.getIdentificationNumber());
        }
            //Compara campos vacios y asigna
        if (customerPresenter.getNames()!=null){
            customer.setNames(customerPresenter.getNames());
        }
        if (customerPresenter.getIdentificationType()!=null){
            customer.setIdentificationType(customerPresenter.getIdentificationType());
        }
        if (customerPresenter.getPhoneNumber()!=null){
            customer.setPhoneNumber(customerPresenter.getPhoneNumber());
        }
        if (customerPresenter.getEmail()!=null){
            customer.setEmail(customerPresenter.getEmail());
        }

        //Edita el cliente
        customer = customerRepository.save(customer);


        return customerToPresenter(customer);


    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Customer doesn't exist"));
        customerRepository.delete(customer);
    }

    private CustomerPresenter customerToPresenter(Customer customer){
        CustomerPresenter customerPresenter1 = modelMapper.map(customer, CustomerPresenter.class);
        Address address = findPrincipalAddress(customer);
        customerPresenter1.setMainAddress(address.getAddress());
        customerPresenter1.setMainCity(address.getCity());
        customerPresenter1.setMainProvince(address.getProvince());
        return customerPresenter1;
    }
    private Customer presenterToCustomer(CustomerPresenter customerPresenter){
        return this.modelMapper.map(customerPresenter, Customer.class);
    }
    private CustomerPresenter addPrincipalAddress(Customer customer) {
        Address address = findPrincipalAddress(customer);
        CustomerPresenter customerPresenter = modelMapper.map(customer, CustomerPresenter.class);
        customerPresenter.setMainCity(address.getCity());
        customerPresenter.setMainProvince(address.getProvince());
        customerPresenter.setMainAddress(address.getAddress());
        return customerPresenter;
    }

    private Address findPrincipalAddress(Customer customer) {
        return customer.getListAddresses().stream().filter(Address::isPrincipal).findFirst().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "No principal address found"));
    }


}
