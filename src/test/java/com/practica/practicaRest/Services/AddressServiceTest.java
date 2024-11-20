package com.practica.practicaRest.Services;

import com.practica.practicaRest.Utils.TestData;
import com.practica.practicaRest.presenters.AddressPresenter;

import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.repositories.AddressRepository;
import com.practica.practicaRest.services.AddressService;
import com.practica.practicaRest.services.CustomerService;
import com.practica.practicaRest.services.Impl.AddressServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AddressService addressService = new AddressServiceImpl();

    @Spy
    private ModelMapper modelMapper;
    @BeforeEach
    public void prepare() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void shouldCreateAddress(){
        //Assert
        Address address = TestData.getInstance().address();
        AddressPresenter addressPresenter = TestData.getInstance().addressDto();
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(customerService.getCustomerById(any(Long.class))).thenReturn(TestData.getInstance().customer());
        //Act
        AddressPresenter response = addressService.createAddress(addressPresenter);
        //Arrange
        verify(addressRepository,times(1)).save(any());
        Assertions.assertThat(address.getId()).isEqualTo(response.getId());
        Assertions.assertThat(address.getAddress()).isEqualTo(response.getAddress());
        Assertions.assertThat(address.getCity()).isEqualTo(response.getCity());
        Assertions.assertThat(address.getProvince()).isEqualTo(response.getProvince());
        Assertions.assertThat(address.getCustomer().getId()).isEqualTo(response.getCustomerId());
        Assertions.assertThat(address.isPrincipal()).isEqualTo(response.isPrincipal());

    }

    @Test
    void shouldSearchByCustomer(){
        //Assert
        List<Address> addresses = List.of(TestData.getInstance().address());
        when(addressRepository.findByCustomerId(any())).thenReturn(addresses);
        //Act
        List<AddressPresenter> response = addressService.searchByCustomer(any());
        //Arrange
        verify(addressRepository,times(1)).findByCustomerId(any());
        Assertions.assertThat(addresses.get(0).getCustomer().getId()).isEqualTo(response.get(0).getCustomerId());
        Assertions.assertThat(addresses.get(0).getAddress()).isEqualTo(response.get(0).getAddress());
        Assertions.assertThat(addresses.get(0).getProvince()).isEqualTo(response.get(0).getProvince());
        Assertions.assertThat(addresses.get(0).getCity()).isEqualTo(response.get(0).getCity());
        Assertions.assertThat(addresses.get(0).isPrincipal()).isEqualTo(response.get(0).isPrincipal());
        Assertions.assertThat(addresses.get(0).getId()).isEqualTo(response.get(0).getId());
    }

    @Test
    void shouldSearchPrincipalAddress() {
        // Arrange
        List<Address> addresses = List.of(TestData.getInstance().address());
        addresses.get(0).setPrincipal(true);
        when(addressRepository.findByCustomerId(any(Long.class))).thenReturn(addresses);

        // Act
        AddressPresenter response = addressService.searchPrincipalAddress(1L);

        // Assert
        verify(addressRepository, times(1)).findByCustomerId(any(Long.class));
        Assertions.assertThat(response.getId()).isEqualTo(addresses.get(0).getId());
        Assertions.assertThat(response.getAddress()).isEqualTo(addresses.get(0).getAddress());
        Assertions.assertThat(response.getCity()).isEqualTo(addresses.get(0).getCity());
        Assertions.assertThat(response.getProvince()).isEqualTo(addresses.get(0).getProvince());
        Assertions.assertThat(response.getCustomerId()).isEqualTo(addresses.get(0).getCustomer().getId());
        Assertions.assertThat(response.isPrincipal()).isEqualTo(addresses.get(0).isPrincipal());
    }
    @Test
    void shouldFailSearchPrincipalAddress() {
        // Arrange
        List<Address> addresses = new ArrayList<>();
        when(addressRepository.findByCustomerId(any())).thenReturn(addresses);

        // Act y Assert
        ResponseStatusException responseStatusException = assertThrows(
                ResponseStatusException.class, () ->addressService.searchPrincipalAddress(any())
        );
        Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        Assertions.assertThat(responseStatusException.getMessage()).contains("No Principal Address Asigned");


    }


}
