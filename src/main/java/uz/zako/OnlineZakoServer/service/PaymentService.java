package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Payment;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.PaymentReq;
import uz.zako.OnlineZakoServer.payloads.response.PaymentRes;

import java.util.List;

public interface PaymentService {
    Result save(PaymentReq paymentReq);
    Result edit(PaymentReq paymentReq, Long id);
    Result delete(Long id);
    Payment findById(Long id);
    Page<Payment> findPaymentPage(Long userId, int page, int size);
    List<PaymentRes> findAll(Long userId);
}
