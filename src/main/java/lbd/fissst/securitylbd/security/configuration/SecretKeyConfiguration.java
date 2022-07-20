package lbd.fissst.securitylbd.security.configuration;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@AllArgsConstructor
@Configuration
public class SecretKeyConfiguration {

    private JwtConfiguration jwtConfiguration;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes());
    }
}
