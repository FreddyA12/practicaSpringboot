package com.practica.practicaRest.presenters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPresenter {
    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String email;
    private String phoneNumber;
    private String mainProvince;
    private String mainCity;
    private String mainAddress;

}
