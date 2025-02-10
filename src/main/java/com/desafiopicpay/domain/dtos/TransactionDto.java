package com.desafiopicpay.domain.dtos;

import com.desafiopicpay.domain.users.User;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal amount,
                             User sender,
                             User receiver) {


}
