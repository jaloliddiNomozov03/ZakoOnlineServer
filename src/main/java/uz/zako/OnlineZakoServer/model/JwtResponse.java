package uz.zako.OnlineZakoServer.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JwtResponse {
    private String type="Bearer";
    private String token;
    public JwtResponse(String token) {
        this.token = token;
    }
}
