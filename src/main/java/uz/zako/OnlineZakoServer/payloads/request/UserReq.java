package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReq {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String hashCode;
    private String aboutUz;
    private String aboutRu;
    private String telegram;
    private String instagram;
    private String facebook;
    private Boolean AccountNonLocked;
}
