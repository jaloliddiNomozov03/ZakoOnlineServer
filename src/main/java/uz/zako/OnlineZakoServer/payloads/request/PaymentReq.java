package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.zako.OnlineZakoServer.entity.Module;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentReq {
    private Long id;
    private Long userId;
    private Long courseId;
    private List<Module> modules;
}
