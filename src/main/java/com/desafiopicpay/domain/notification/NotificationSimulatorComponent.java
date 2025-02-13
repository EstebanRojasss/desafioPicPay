package com.desafiopicpay.domain.notification;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationSimulatorComponent {

    private String status;
    private Data data;

    public String getStatus(){
        return status;
    }

    public Data data(){
        return data;
    }

    public static class Data{
        private String message;
        public String getMessage() {
            return message;
        }
    }
}
