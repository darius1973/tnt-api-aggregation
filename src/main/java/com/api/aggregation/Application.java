package com.api.aggregation;

import com.api.aggregation.properties.WebServiceConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private WebServiceConfigProperties webServiceConfigProperties;

    @Bean
    WebClient webClient() {
        return WebClient.create(webServiceConfigProperties.getHost());
    }
}
