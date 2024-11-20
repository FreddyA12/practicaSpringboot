package com.practica.practicaRest.presenters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressPresenter {
    private Long id;
    private boolean principal;
    private String province;
    private String city;
    private String address;
    private Long customerId;

}
