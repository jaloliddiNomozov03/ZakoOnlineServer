package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Payment;
import uz.zako.OnlineZakoServer.entity.User;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAllByUser(User user, Pageable pageable);
    List<Payment> findAllByUserOrderByCreateAtDesc(User user);
    List<Payment> findAllByUserId(Long userId);
}
