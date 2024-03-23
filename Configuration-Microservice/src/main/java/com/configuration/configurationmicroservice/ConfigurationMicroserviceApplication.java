package com.configuration.configurationmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigurationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationMicroserviceApplication.class, args);
    }

}
