package com.authentication.authenticationmicroservice.Configs;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@EnableWebSecurity
@Configuration


public class SecurityConfig {

    //private RSAKeysConfig rsaKeysConfig;
    private final RSAKeyConfig rsaKeyConfig;

    private final ResourceLoader resourceLoader;

    public SecurityConfig(RSAKeyConfig rsaKeyConfig, ResourceLoader resourceLoader) {
        this.rsaKeyConfig = rsaKeyConfig;
        this.resourceLoader = resourceLoader;
    }
    private RSAPublicKey getPublicKey() throws Exception {
        Resource resource = resourceLoader.getResource(rsaKeyConfig.getPublicKey());
        String key = new String(StreamUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        return parsePublicKey(key);
    }
    private RSAPrivateKey getPrivateKey() throws Exception {
        Resource resource = resourceLoader.getResource(rsaKeyConfig.getPrivateKey());
        String key = new String(StreamUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        return parsePrivateKey(key);
    }

    private RSAPublicKey parsePublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Implementation to parse public key
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
    private RSAPrivateKey parsePrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Implementation to parse private key
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }


    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(User.withUsername("user1").password("{noop}1234").authorities("USER").build(), User.withUsername("user2").password("{noop}1234").authorities("USER").build(), User.withUsername("admin").password("{noop}1234").authorities("ADMIN", "USER").build());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.anyRequest()
                        .authenticated())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
            try {
                jwt.decoder(jwtDecoder());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).httpBasic(Customizer.withDefaults()).build();
    }


    @Bean
    public JwtDecoder jwtDecoder() throws Exception {

        return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAPublicKey publicKey = getPublicKey();
        RSAPrivateKey privateKey = getPrivateKey();
        //JWK jwk = new RSAKey.Builder(rsaKeysConfig.publicKey()).privateKey(rsaKeysConfig.privateKey()).build();
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);

    }

}