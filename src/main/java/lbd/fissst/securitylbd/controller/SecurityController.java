package lbd.fissst.securitylbd.controller;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lbd.fissst.securitylbd.DTO.AuthenticationResponse;
import lbd.fissst.securitylbd.DTO.RefreshJWTRequest;
import lbd.fissst.securitylbd.security.configuration.JwtConfiguration;
import lbd.fissst.securitylbd.service.implementation.AppUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final SecretKey secretKeyAccessToken;

    private final SecretKey secretKeyRefreshToken;

    private final AppUserServiceImpl userService;

    private final JwtConfiguration jwtConfiguration;

    public SecurityController(@Qualifier("secretKeyAccessToken") SecretKey secretKeyAccessToken,
                              @Qualifier("secretKeyRefreshToken") SecretKey secretKeyRefreshToken,
                              AppUserServiceImpl userService,
                              JwtConfiguration jwtConfiguration) {
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
        this.userService = userService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshJWT(@RequestBody RefreshJWTRequest request){

        try{
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKeyRefreshToken)
                    .parseClaimsJws(request.getRefreshToken());

            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            UserDetails user = userService.loadUserByUsername(username);

            String accessToken = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("authorities", user.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getAccessTokenExpirationAfterDays())))
                    .signWith(secretKeyAccessToken)
                    .compact();

            return ResponseEntity.ok()
                    .body(
                            new AuthenticationResponse(
                                    accessToken,
                                    request.getRefreshToken()
                            )
                    );

        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
    }
}
