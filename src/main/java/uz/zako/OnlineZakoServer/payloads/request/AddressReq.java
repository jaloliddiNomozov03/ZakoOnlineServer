package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AddressReq {
    private Long id;

    private Long userId;

    private String region;

    private String district;

    private String street;

    private String home;
}
