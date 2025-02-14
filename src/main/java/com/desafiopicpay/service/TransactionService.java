package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.NotificationDto;
import com.desafiopicpay.domain.dtos.TransactionDto;
import com.desafiopicpay.domain.transactions.AuthenticateTransaction;
import com.desafiopicpay.domain.transactions.Transaction;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.exceptions.DoYourExceptions;
import com.desafiopicpay.mapper.transaction.TransactionToTransactionDto;
import com.desafiopicpay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    private final RestTemplate restTemplate;

    private final static String AUTH_URI = "https://util.devi.tools/api/v2/authorize";

    private final TransactionToTransactionDto mapTransactionToDto;

    private final NotificationService notificationService;

    @Transactional
    public TransactionDto createTransaction(TransactionDto dtoTransaction) {
        User sender = userService.findById(dtoTransaction.sender().getId());
        User receiver = userService.findById(dtoTransaction.receiver().getId());

        userService.validateTransaction(sender, sender.getBalance());

        if (!authenticateTransaction()) {
            throw new DoYourExceptions("Transacción no autorizada.\n", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(dtoTransaction.amount());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setLocalDateTime(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(dtoTransaction.amount()));
        receiver.setBalance(receiver.getBalance().add(dtoTransaction.amount()));

        transactionRepository.save(transaction);
        sendNotification(sender, "Transacción realizada con éxito.");
        sendNotification(receiver, "Transacción recibida con éxito");

        return mapTransactionToTransactionDto(transaction);
    }


    public Boolean authenticateTransaction() {
        AuthenticateTransaction authTransaction = restTemplate.getForObject(AUTH_URI, AuthenticateTransaction.class);
        try {
            if (authTransaction.getData() != null && authTransaction.getData().isAuthorization()) {
                return true;
            }
        } catch (Exception e) {
            log.info("Ocurrió un error con la autenticación.\n");
        }
        return false;
    }

    public TransactionDto mapTransactionToTransactionDto(Transaction transaction) {
        return mapTransactionToDto.map(transaction);
    }

    public void sendNotification(User user, String message) {
        notificationService.sendNotification(user, message);
    }


}
