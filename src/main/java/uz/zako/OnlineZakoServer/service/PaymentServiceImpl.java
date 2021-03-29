package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Payment;
import uz.zako.OnlineZakoServer.entity.PaymentDetail;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.CourseReq;
import uz.zako.OnlineZakoServer.payloads.request.PaymentReq;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;
import uz.zako.OnlineZakoServer.payloads.response.PaymentRes;
import uz.zako.OnlineZakoServer.repository.PaymentRepository;
import uz.zako.OnlineZakoServer.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private PaymentDetailService paymentDetailService;
    @Override
    public Result save(PaymentReq paymentReq) {
        try {
            Payment payment=new Payment();
            payment.setUser(userService.findById(paymentReq.getUserId()));
            payment.setModules(paymentReq.getModules());
            payment.setCourse(courseService.findById(paymentReq.getCourseId()));
            paymentRepository.save(payment);
            return new Result(true, String.valueOf(payment.getId()));
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }

    @Override
    public Result edit(PaymentReq paymentReq, Long id) {
        try {
            Payment payment=paymentRepository.findById(id).get();
            payment.setUser(userService.findById(paymentReq.getUserId()));
            payment.setModules(paymentReq.getModules());
            payment.setCourse(courseService.findById(paymentReq.getCourseId()));
            paymentRepository.save(payment);
            return new Result(true,"editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"editing failed");
    }
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Result delete(Long id) {
        try {
//            Payment payment=paymentRepository.findById(id).get();
            PaymentDetail paymentDetail=paymentDetailService.findByPaymentId(id);
            if (paymentDetail!=null) {
                paymentDetailService.delete(paymentDetail.getId());
            }
            paymentRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"deleting failed");
    }
    void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public Payment findById(Long id) {
        try {
            return paymentRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<PaymentRes> findAll(Long userId) {
        try {
            List<Payment> list=paymentRepository.findAllByUserOrderByCreateAtDesc(userService.findById(userId));
            List<PaymentRes> paymentRes=new ArrayList<>();
            for (Payment payment : list) {
                PaymentRes res=new PaymentRes();

                CourseReq courseReq=new CourseReq();
                courseReq.setId(payment.getId());
                courseReq.setTitleUz(payment.getCourse().getTitleUz());
                courseReq.setTitleRu(payment.getCourse().getTitleRu());

                res.setId(payment.getId());

                res.setCourseReq(courseReq);

                UserReq userReq=new UserReq();
                userReq.setId(payment.getUser().getId());
                userReq.setFirstName(payment.getUser().getFirstName());
                userReq.setLastName(payment.getUser().getLastName());

                res.setUserReq(userReq);
                res.setModules(payment.getModules());
                res.setCreateAt(payment.getCreateAt());
                paymentRes.add(res);
            }
            return paymentRes;
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<Payment> findPaymentPage(Long userId, int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
            return paymentRepository.findAllByUser(userService.findById(userId), pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
