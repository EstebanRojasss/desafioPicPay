package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.NotificationDto;
import com.desafiopicpay.domain.notification.NotificationSimulatorComponent;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.exceptions.DoYourExceptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final RestTemplate restTemplate;

    private final static String NOTIFICATION_URI = "https://util.devi.tools/api/v1/notify";

    public void sendNotification(User receiver, String message){
        String email = receiver.getEmail();

        NotificationDto notification = new NotificationDto(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.
                postForEntity(NOTIFICATION_URI, notification, String.class);
        if(notificationResponse.getStatusCode() != HttpStatus.OK){
            throw new DoYourExceptions("Error en el envio de la notificaion.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
