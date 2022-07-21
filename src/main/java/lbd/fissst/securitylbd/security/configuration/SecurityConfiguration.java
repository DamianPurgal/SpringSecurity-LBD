package lbd.fissst.securitylbd.security.configuration;

import io.jsonwebtoken.security.Keys;
import lbd.fissst.securitylbd.security.filter.JwtAuthenticationFilter;
import lbd.fissst.securitylbd.security.filter.JwtAuthorizationFilter;
import lbd.fissst.securitylbd.service.implementation.AppUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserServiceImpl appUserService;

    private final PasswordEncoder passwordEncoder;

    private final JwtConfiguration jwtConfiguration;

    private final SecretKey secretKeyAccessToken;

    private final SecretKey secretKeyRefreshToken;

    public SecurityConfiguration(AppUserServiceImpl appUserService,
                                 PasswordEncoder passwordEncoder,
                                 JwtConfiguration jwtConfiguration,
                                 @Qualifier("secretKeyAccessToken") SecretKey secretKeyAccessToken,
                                 @Qualifier("secretKeyRefreshToken") SecretKey secretKeyRefreshToken) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfiguration = jwtConfiguration;
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfiguration, secretKeyAccessToken, secretKeyRefreshToken))
                .addFilterAfter(new JwtAuthorizationFilter(jwtConfiguration, secretKeyAccessToken), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/user").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/refreshToken").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(appUserService)
                .passwordEncoder(passwordEncoder);
    }

}
