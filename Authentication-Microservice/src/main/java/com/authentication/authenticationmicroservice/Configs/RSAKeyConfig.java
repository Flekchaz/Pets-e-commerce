package com.authentication.authenticationmicroservice.Configs;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter @Setter
@ConfigurationProperties(prefix = "rsa")
public class RSAKeyConfig {

    private String privateKey;
    private String publicKey;



}
