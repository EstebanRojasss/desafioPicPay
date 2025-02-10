package com.desafiopicpay.domain.dtos;

import com.desafiopicpay.domain.users.UserType;

import java.math.BigDecimal;

public record UserDto(String name,
                      String lastName,
                      String email,
                      String document,
                      String password,
                      UserType userType,
                      BigDecimal balance){

    public UserDto(String name,
                   String lastName,
                   String email,
                   String document,
                   UserType userType,
                   BigDecimal balance) {
        this(name, lastName, email, document, "", userType, balance);
    }

}
