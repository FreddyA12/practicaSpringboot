package com.practica.practicaRest.Controllers;

import com.practica.practicaRest.Utils.TestData;
import com.practica.practicaRest.controllers.CustomerController;
import com.practica.practicaRest.presenters.CustomerPresenter;
import com.practica.practicaRest.services.CustomerService;
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
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    @Test
    void shouldGetCustomersByIdOrName(){
        //Assert
        List<CustomerPresenter> customerPresenters = List.of(TestData.getInstance().customerDto());
        when(customerService.searchCustomers(any(), any())).thenReturn(customerPresenters);
        //Act
        List<CustomerPresenter> response = customerController.getCustomersByIdOrName("","");
        //Arrange
        verify(customerService,times(1)).searchCustomers("","");
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void shouldSaveCustomer(){
        //Assert
        CustomerPresenter customerPresenter = TestData.getInstance().customerDto();
        when(customerService.saveCustomer(any(customerPresenter.getClass()))).thenReturn(customerPresenter);
        //Act
        CustomerPresenter response = customerController.saveCustomer(customerPresenter);
        //Arrange
        verify(customerService,times(1)).saveCustomer(customerPresenter);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void shouldEditCustomer(){
        //Assert
        CustomerPresenter customerPresenter = TestData.getInstance().customerDto();
        when(customerService.editCustomer(any(customerPresenter.getClass()))).thenReturn(customerPresenter);
        //Act
        CustomerPresenter response = customerController.editCustomer(customerPresenter);
        //Arrange
        verify(customerService,times(1)).editCustomer(customerPresenter);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void shouldDeleteCustomer(){
        //Assert
        CustomerPresenter customerPresenter = TestData.getInstance().customerDto();
        //Act
        customerController.deleteCustomer(1L);
        //Arrange
        verify(customerService,times(1)).deleteCustomer(1L);
    }

}
