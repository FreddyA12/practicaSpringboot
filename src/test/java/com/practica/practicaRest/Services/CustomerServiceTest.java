package com.practica.practicaRest.Services;

import com.practica.practicaRest.Utils.TestData;
import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.dtos.CustomerDto;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.entities.Customer;
import com.practica.practicaRest.repositories.AddressRepository;
import com.practica.practicaRest.repositories.CustomerRepository;
import com.practica.practicaRest.services.AddressService;
import com.practica.practicaRest.services.CustomerService;
import com.practica.practicaRest.services.Impl.AddressServiceImpl;
import com.practica.practicaRest.services.Impl.CustomerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService = new CustomerServiceImpl();
    @Mock
    private AddressService addressService = new AddressServiceImpl();
    @Spy
    private ModelMapper modelMapper;
    @BeforeEach
    public void prepare() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void shouldSearchCustomersByIdentificationNumber(){
        //Arrange
        List<Customer> customerList = List.of(TestData.getInstance().customer());
        AddressDto address = TestData.getInstance().addressDto();
        when(customerRepository.findByIdentificationNumberContaining(any())).thenReturn(customerList);
        when(addressService.searchPrincipalAddress(any())).thenReturn(address);
        //Act
        List<CustomerDto> response = customerService.searchCustomers("18",null);
        //Assert
        verify(customerRepository,times(1)).findByIdentificationNumberContaining(any());
        Assertions.assertThat(customerList.get(0).getId()).isEqualTo(response.get(0).getId());
        Assertions.assertThat(customerList.get(0).getIdentificationNumber()).isEqualTo(response.get(0).getIdentificationNumber());
        Assertions.assertThat(customerList.get(0).getPhoneNumber()).isEqualTo(response.get(0).getPhoneNumber());
        Assertions.assertThat(customerList.get(0).getNames()).isEqualTo(response.get(0).getNames());
        Assertions.assertThat(customerList.get(0).getEmail()).isEqualTo(response.get(0).getEmail());
        Assertions.assertThat(customerList.get(0).getIdentificationType()).isEqualTo(response.get(0).getIdentificationType());

    }

    @Test
    void shouldSearchCustomersByName(){
        //Arrange
        List<Customer> customerList = List.of(TestData.getInstance().customer());
        AddressDto address = TestData.getInstance().addressDto();
        when(customerRepository.findByNamesContaining(any())).thenReturn(customerList);
        when(addressService.searchPrincipalAddress(any())).thenReturn(address);
        //Act
        List<CustomerDto> response = customerService.searchCustomers(null,"none");
        //Assert
        verify(customerRepository,times(1)).findByNamesContaining(any());
        Assertions.assertThat(customerList.get(0).getId()).isEqualTo(response.get(0).getId());
        Assertions.assertThat(customerList.get(0).getIdentificationNumber()).isEqualTo(response.get(0).getIdentificationNumber());
        Assertions.assertThat(customerList.get(0).getPhoneNumber()).isEqualTo(response.get(0).getPhoneNumber());
        Assertions.assertThat(customerList.get(0).getNames()).isEqualTo(response.get(0).getNames());
        Assertions.assertThat(customerList.get(0).getEmail()).isEqualTo(response.get(0).getEmail());
        Assertions.assertThat(customerList.get(0).getIdentificationType()).isEqualTo(response.get(0).getIdentificationType());

    }


    @Test
    void shouldSaveCustomer(){
        //Arrange
        Customer customer = TestData.getInstance().customer();
        customer.setListAddresses(new ArrayList<>());
        customer.getListAddresses().add(TestData.getInstance().address());
        when(customerRepository.save(any())).thenReturn(customer);
        //Act
        CustomerDto response = customerService.saveCustomer(TestData.getInstance().customerDto());
        //Assert
        verify(customerRepository,times(1)).save(any());
        Assertions.assertThat(customer.getId()).isEqualTo(response.getId());
        Assertions.assertThat(customer.getIdentificationType()).isEqualTo(response.getIdentificationType());
        Assertions.assertThat(customer.getEmail()).isEqualTo(response.getEmail());
        Assertions.assertThat(customer.getNames()).isEqualTo(response.getNames());
        Assertions.assertThat(customer.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        Assertions.assertThat(customer.getIdentificationNumber()).isEqualTo(response.getIdentificationNumber());
        Assertions.assertThat(customer.getListAddresses().get(0).getAddress()).isEqualTo(response.getMainAddress());
        Assertions.assertThat(customer.getListAddresses().get(0).getProvince()).isEqualTo(response.getMainProvince());
        Assertions.assertThat(customer.getListAddresses().get(0).getCity()).isEqualTo(response.getMainCity());


    }

    @Test
    void shouldFailSaveCustomerWhenIdentificationNumberAlreadyExists() {
        // Arrange
        Customer customer = TestData.getInstance().customer();
        CustomerDto customerDto = TestData.getInstance().customerDto();
        when(customerRepository.findByIdentificationNumber(any())).thenReturn(List.of(customer));
        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->customerService.saveCustomer(customerDto)
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("Identification Number already exists");


    }
    @Test
    void shouldEditCustomer(){
        //Arrange
        Customer customer = TestData.getInstance().customer();
        customer.setNames("TEST");
        CustomerDto editado = TestData.getInstance().customerDto();
        editado.setNames("TEST");
        customer.setListAddresses(new ArrayList<>());
        customer.getListAddresses().add(TestData.getInstance().address());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerRepository.findByIdentificationNumber(any())).thenReturn(new ArrayList<>());
        when(addressService.searchPrincipalAddress(any())).thenReturn(TestData.getInstance().addressDto());

        //Act
        CustomerDto response = customerService.editCustomer(editado);

        //Assert
        verify(customerRepository,times(1)).save(any(Customer.class));
        Assertions.assertThat(customer.getNames()).isEqualTo(response.getNames());

    }

    @Test
    void shouldFailEditWhenNoId(){
        // Arrange
        Customer customer = TestData.getInstance().customer();
        CustomerDto customerDto = TestData.getInstance().customerDto();
        customerDto.setId(null);
        //when(customerRepository.findByIdentificationNumber(any())).thenReturn(List.of(customer));
        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->customerService.editCustomer(customerDto)
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("Send an id");

    }

    @Test
    void shouldFailEditWhenCustomerDoesntExist(){
        // Arrange
        Customer customer = TestData.getInstance().customer();
        CustomerDto customerDto = TestData.getInstance().customerDto();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->customerService.editCustomer(customerDto)
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("Customer doesn't exist");

    }

    @Test
    void shouldFailEditWhenIdentificationNumberAlreadyExist(){
        // Arrange
        Customer customer = TestData.getInstance().customer();
        CustomerDto customerDto = TestData.getInstance().customerDto();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findByIdentificationNumber(any())).thenReturn(List.of(customer));

        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->customerService.editCustomer(customerDto)
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("Identification Number already exists");

    }

    @Test
    void shouldDeleteCustomer(){
        //Arrange
        Customer customer = TestData.getInstance().customer();
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        //Act
        customerService.deleteCustomer(1L);
        //Assert
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void shouldFailDelete(){
        //Arrange
        Customer customer = TestData.getInstance().customer();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->customerService.deleteCustomer(1L)
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("Customer doesn't exist");

    }




}
