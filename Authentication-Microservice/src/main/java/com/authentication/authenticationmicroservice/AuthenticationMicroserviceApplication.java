package com.authentication.authenticationmicroservice;

import com.authentication.authenticationmicroservice.Configs.RSAKeyConfig;
import com.authentication.authenticationmicroservice.Configs.RSAKeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyConfig.class)
public class AuthenticationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationMicroserviceApplication.class, args);
    }

}
