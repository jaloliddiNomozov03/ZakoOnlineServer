package uz.zako.OnlineZakoServer.service;

import uz.zako.OnlineZakoServer.entity.PaymentDetail;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.PaymentDetailReq;

import java.util.List;

public interface PaymentDetailService {
    Result save(PaymentDetailReq paymentDetail);
    Result edit(PaymentDetailReq paymentDetail, Long id);
    Result delete(Long id);
    PaymentDetail findById(Long id);
    PaymentDetail findByPaymentId(Long id);
    List<PaymentDetail> findAllList();
}
