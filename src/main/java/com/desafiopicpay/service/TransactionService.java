package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.TransactionDto;
import com.desafiopicpay.domain.transactions.Transaction;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.exceptions.DoYourExceptions;
import com.desafiopicpay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    private final RestTemplate restTemplate;

    private final static String AUTH_URI = "https://util.devi.tools/api/v2/authorize";

    public void createTransaction(TransactionDto dtoTransaction) {
        User sender = userService.findById(dtoTransaction.sender().getId());
        User receiver = userService.findById(dtoTransaction.receiver().getId());

        userService.validateTransaction(sender, sender.getBalance());

        if(!authenticateTransaction()){
            throw new DoYourExceptions("Transacción no autorizada.", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(dtoTransaction.amount());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setLocalDateTime(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(dtoTransaction.amount()));
        receiver.setBalance(receiver.getBalance().add(dtoTransaction.amount()));
    }


    public Boolean authenticateTransaction() {
        ResponseEntity<Map> authResponse = restTemplate.getForEntity(AUTH_URI, Map.class);
        if(authResponse.getStatusCode() != HttpStatus.OK){
            return false;
        }
        return Objects.requireNonNull(authResponse.getBody()).get("authorization") == "true";
    }

}
