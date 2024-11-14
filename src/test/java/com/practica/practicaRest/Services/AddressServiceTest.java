package com.practica.practicaRest.Services;

import com.practica.practicaRest.Utils.TestData;
import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.dtos.CustomerDto;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.repositories.AddressRepository;
import com.practica.practicaRest.services.AddressService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
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
        AddressDto addressDto = TestData.getInstance().addressDto();
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        //Act
        AddressDto response = addressService.createAddress(addressDto);
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
        List<AddressDto> response = addressService.searchByCustomer(any());
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
    void shouldSearchPrincipalAddress(){
        //Assert
        List<Address> addresses = List.of(TestData.getInstance().address());
        addresses.get(0).setPrincipal(true);
        when(addressRepository.findByCustomerId(any())).thenReturn(addresses);
        //Act
        AddressDto response = addressService.searchPrincipalAddress(any());
        //Arrange
        verify(addressRepository,times(1)).findByCustomerId(any());
        Assertions.assertThat(addresses.get(0).getId()).isEqualTo(response.getId());
        Assertions.assertThat(addresses.get(0).getAddress()).isEqualTo(response.getAddress());
        Assertions.assertThat(addresses.get(0).getCity()).isEqualTo(response.getCity());
        Assertions.assertThat(addresses.get(0).getProvince()).isEqualTo(response.getProvince());
        Assertions.assertThat(addresses.get(0).getCustomer().getId()).isEqualTo(response.getCustomerId());
        Assertions.assertThat(addresses.get(0).isPrincipal()).isEqualTo(response.isPrincipal());
    }

}
