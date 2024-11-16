package com.practica.practicaRest.services.Impl;

import com.practica.practicaRest.presenters.AddressPresenter;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.repositories.AddressRepository;
import com.practica.practicaRest.services.AddressService;
import com.practica.practicaRest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private CustomerService customerService;
    public AddressServiceImpl (){
        this.modelMapper = new ModelMapper();
    }

    @Override
    public AddressPresenter createAddress(AddressPresenter addressPresenter) {
        Address address = addressRepository.save(this.presenterToAddress(addressPresenter));
        return this.addressToPresenter(address);
    }

    @Override
    public List<AddressPresenter> searchByCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId).stream().map(this::addressToPresenter).collect(Collectors.toList());
    }


    @Override
    public AddressPresenter searchPrincipalAddress(Long idAutor) {
        List<Address> addresses =  addressRepository.findByCustomerId(idAutor);
        if (addresses.isEmpty()){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "No Principal Address Asigned");
        }else {
            return this.addressToPresenter(addresses.stream().filter(address -> address.isPrincipal()).collect(Collectors.toList()).get(0));
        }
    }

    public AddressPresenter addressToPresenter(Address address){
        AddressPresenter addressPresenter =  modelMapper.map(address, AddressPresenter.class);
        addressPresenter.setCustomerId(address.getCustomer().getId());
        return addressPresenter;
    }

    public Address presenterToAddress(AddressPresenter addressPresenter){
        Address address = modelMapper.map(addressPresenter, Address.class);
        return address;
    }
}
