package com.practica.practicaRest.services.Impl;

import com.practica.practicaRest.dtos.AddressDto;
import com.practica.practicaRest.entities.Address;
import com.practica.practicaRest.repositories.AddressRepository;
import com.practica.practicaRest.services.AddressService;
import com.practica.practicaRest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public AddressDto newAddress(AddressDto addressDto) {
        Address address = addressRepository.save(this.dtoToAddress(addressDto));
        return this.addressToDto(address);
    }

    @Override
    public List<AddressDto> searchByCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId).stream().map(this::addressToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = this.addressRepository.findById(addressId).orElse(null);
        if (address != null){
            addressRepository.delete(address);
        }
    }

    @Override
    public AddressDto searchPrincipalAddress(Long idAutor) {
        List<Address> addresses =  addressRepository.findByCustomerId(idAutor);
        if (addresses.isEmpty()){
            //Error Exception
        }else{
            //Return the first
        }

        return this.addressToDto(addresses.stream().filter(address-> address.isPrincipal()).collect(Collectors.toList()).get(0));
    }

    public AddressDto addressToDto(Address address){
        AddressDto addressDto =  modelMapper.map(address, AddressDto.class);
        addressDto.setCustomerId(address.getCustomer().getId());
        return addressDto;
    }

    public Address dtoToAddress(AddressDto  addressDto){
        Address address = modelMapper.map(addressDto, Address.class);
        //address.setCustomer(customerService.searchCustomer);
        return address;
    }
}
