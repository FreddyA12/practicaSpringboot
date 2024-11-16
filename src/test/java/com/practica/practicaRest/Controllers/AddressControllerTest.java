package com.practica.practicaRest.Controllers;

import com.practica.practicaRest.Utils.TestData;
import com.practica.practicaRest.controllers.AddressController;
import com.practica.practicaRest.presenters.AddressPresenter;
import com.practica.practicaRest.services.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {
    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Test
    void shouldCreateAditionalAddress(){
        //Arrange
        AddressPresenter addressPresenter = TestData.getInstance().addressDto();
        addressPresenter.setPrincipal(false);

        when(addressService.createAddress(any(AddressPresenter.class))).thenReturn(addressPresenter);
        //Act
        AddressPresenter result =addressController.createAditionalAddress(addressPresenter);
        //Assert
        verify(addressService, times(1)).createAddress(any());
        Assertions.assertThat(result).isNotNull();
    }
    @Test
    void shouldGetAddressesByCustomer(){
        //Arrange
        List<AddressPresenter> list = List.of(TestData.getInstance().addressDto());
        when(addressService.searchByCustomer(any())).thenReturn(list);
        //Act
        List<AddressPresenter> result = addressController.getAddressesByCustomer(1L);
        //Assert
        verify(addressService,times(1)).searchByCustomer(1L);
        Assertions.assertThat(result).isNotNull();
    }
}
