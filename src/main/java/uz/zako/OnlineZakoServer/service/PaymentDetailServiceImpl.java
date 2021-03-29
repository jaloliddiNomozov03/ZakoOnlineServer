package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.PaymentDetail;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.PaymentDetailReq;
import uz.zako.OnlineZakoServer.repository.PaymentDetailRepository;
import uz.zako.OnlineZakoServer.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {
    @Autowired
    private PaymentDetailRepository paymentDetailRepository;
    @Autowired
    private PaymentService paymentService;
    @Override
    public Result save(PaymentDetailReq paymentDetail) {
        try {
            PaymentDetail updatePaymentDetail=new PaymentDetail();
            updatePaymentDetail.setTransferId(paymentDetail.getTransferId());
            updatePaymentDetail.setFullName(paymentDetail.getFullName());
            updatePaymentDetail.setPayment(paymentService.findById(paymentDetail.getPaymentId()));
            updatePaymentDetail.setSum(paymentDetail.getSum());
            updatePaymentDetail.setExp(paymentDetail.getExp());
            updatePaymentDetail.setCommissionSum(paymentDetail.getCommissionSum());
            updatePaymentDetail.setCardNumber(paymentDetail.getCardNumber());
            paymentDetailRepository.save(updatePaymentDetail);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result edit(PaymentDetailReq paymentDetail, Long id) {
        try {
            PaymentDetail updatePaymentDetail=paymentDetailRepository.findById(id).get();
            updatePaymentDetail.setTransferId(paymentDetail.getTransferId());
            updatePaymentDetail.setPayment(paymentService.findById(paymentDetail.getPaymentId()));
            updatePaymentDetail.setSum(paymentDetail.getSum());
            updatePaymentDetail.setExp(paymentDetail.getExp());
            updatePaymentDetail.setCommissionSum(paymentDetail.getCommissionSum());
            updatePaymentDetail.setCardNumber(paymentDetail.getCardNumber());
            updatePaymentDetail.setFullName(paymentDetail.getFullName());
            paymentDetailRepository.save(updatePaymentDetail);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            paymentDetailRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"deleting failed");
    }

    @Override
    public PaymentDetail findByPaymentId(Long id) {
        try {
            return paymentDetailRepository.findByPayment(paymentService.findById(id));
        }catch ( Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<PaymentDetail> findAllList() {
        try {
            return paymentDetailRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public PaymentDetail findById(Long id) {
        try {
            return paymentDetailRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
