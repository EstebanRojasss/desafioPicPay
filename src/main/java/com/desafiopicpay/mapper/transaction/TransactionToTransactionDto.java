package com.desafiopicpay.mapper.transaction;

import com.desafiopicpay.domain.dtos.TransactionDto;
import com.desafiopicpay.domain.transactions.Transaction;
import com.desafiopicpay.mapper.IMapper;

public class TransactionToTransactionDto implements IMapper<Transaction, TransactionDto> {

    @Override
    public TransactionDto map(Transaction in) {
        return new TransactionDto(in.getAmount(), in.getSender(), in.getReceiver());
    }
}
