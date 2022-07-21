package lbd.fissst.securitylbd.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJWTRequest {
    private String refreshToken;
}
