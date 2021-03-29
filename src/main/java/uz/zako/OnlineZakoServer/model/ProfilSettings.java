package uz.zako.OnlineZakoServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfilSettings {
    private String oldPassword;
    private String newPassword;
}
