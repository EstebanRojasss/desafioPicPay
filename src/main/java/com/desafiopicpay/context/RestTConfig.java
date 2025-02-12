package com.desafiopicpay.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
