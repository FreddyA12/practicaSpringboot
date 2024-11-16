package com.practica.practicaRest.services;

import com.practica.practicaRest.presenters.AddressPresenter;

import java.util.List;

public interface AddressService {
    AddressPresenter createAddress(AddressPresenter addressPresenter);
    List<AddressPresenter> searchByCustomer(Long customerId);

    AddressPresenter searchPrincipalAddress(Long customerId);
}
