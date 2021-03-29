package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.zako.OnlineZakoServer.entity.Payment;
import uz.zako.OnlineZakoServer.entity.User;
import uz.zako.OnlineZakoServer.model.JwtResponse;
import uz.zako.OnlineZakoServer.model.ProfilSettings;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.LoginReq;
import uz.zako.OnlineZakoServer.payloads.request.UserPasswordReq;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;

import java.util.List;

public interface UserService {
    ResponseEntity<JwtResponse> signin(LoginReq loginRequest);

    UserReq getMe(User user);

    User findById(Long id);

    Result save(UserReq userReq);

    Result edit(Long id, UserReq userReq);

    Page<User> findAll(int page, int size);

    Result delete(Long id);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Result editPassword(ProfilSettings profilSettings, User user);

    Result editPasswordClient(UserPasswordReq userPasswordReq, Long userId);
    List<Payment> getAllPaymentCourseByUserId(Long userId);
}
