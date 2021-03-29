package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    @NotBlank(message = "Bosh bolishi mumkin emas")
    private String username;

    @NotBlank(message = "Bosh bolishi mumkin emas")
    private String password;

}
