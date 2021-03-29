package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetailReq {
    private Long id;
    private Long paymentId;
    private Double sum;
    private String fullName;
    private Double commissionSum;
    private String transferId;
    private String cardNumber;
    private String exp;
}
