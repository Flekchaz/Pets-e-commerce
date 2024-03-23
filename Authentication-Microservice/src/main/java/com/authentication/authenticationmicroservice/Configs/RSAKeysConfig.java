package com.authentication.authenticationmicroservice.Configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;


//@ConfigurationProperties(prefix = "rsa")
public record RSAKeysConfig(RSAPublicKey publicKey, RSAPrivateCrtKey privateKey) {

}
