package uz.zako.OnlineZakoServer.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.payloads.request.CourseReq;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRes {
    private Long id;
    private UserReq userReq;
    private CourseReq courseReq;
    private List<Module> modules;
    private Date createAt;
}
