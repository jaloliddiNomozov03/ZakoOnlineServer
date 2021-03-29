package uz.zako.OnlineZakoServer.service;

import javassist.NotFoundException;
import javassist.expr.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Payment;
import uz.zako.OnlineZakoServer.entity.Role;
import uz.zako.OnlineZakoServer.entity.User;
import uz.zako.OnlineZakoServer.model.JwtProvider;
import uz.zako.OnlineZakoServer.model.JwtResponse;
import uz.zako.OnlineZakoServer.model.ProfilSettings;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.LoginReq;
import uz.zako.OnlineZakoServer.payloads.request.UserPasswordReq;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;
import uz.zako.OnlineZakoServer.repository.PaymentRepository;
import uz.zako.OnlineZakoServer.repository.RoleRepository;
import uz.zako.OnlineZakoServer.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AttachmentService attachmentService;
    private PaymentRepository paymentRepository;
    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           AttachmentService attachmentService,
                           PaymentRepository paymentRepository){
        this.authenticationManager=authenticationManager;
        this.jwtProvider=jwtProvider;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.attachmentService=attachmentService;
        this.paymentRepository=paymentRepository;
    }

    @Override
    public ResponseEntity<JwtResponse> signin(LoginReq loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
    @Override
    public UserReq getMe(User user) {
        if (user!=null){
            UserReq userReq=new UserReq();
            userReq.setId(user.getId());
            userReq.setFirstName(user.getFirstName());
            userReq.setLastName(user.getLastName());
            userReq.setUsername(user.getUsername());
            userReq.setPhoneNumber(user.getPhoneNumber());
            if (user.getAttachment()!=null){
                userReq.setHashCode(user.getAttachment().getHashCode());
            }else {
                userReq.setHashCode(null);
            }
            return userReq;
        }else {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Result save(UserReq userReq) {
        try {
            User user=new User();
            user.setUsername(userReq.getUsername());
            user.setPassword(passwordEncoder.encode(userReq.getPassword()));

            List<Role> list=new ArrayList<>();
            list.add(roleRepository.findByName("ROLE_STUDENT"));
            user.setRoles(list);

            user.setPhoneNumber(userReq.getPhoneNumber());
            user.setAttachment(attachmentService.findByHashCode(userReq.getHashCode()));
            user.setFirstName(userReq.getFirstName());
            user.setLastName(userReq.getLastName());
            userRepository.save(user);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result edit(Long id, UserReq userReq) {
        try {
            User user=userRepository.findById(id).get();
            user.setUsername(userReq.getUsername());
            if (userReq.getPassword()!=null) {
                user.setPassword(passwordEncoder.encode(userReq.getPassword()));
            }
            List<Role> list=new ArrayList<>();
            list.add(roleRepository.findByName("ROLE_STUDENT"));
            user.setRoles(list);

            user.setPhoneNumber(userReq.getPhoneNumber());
            if (userReq.getHashCode()!=null) {
                user.setAttachment(attachmentService.findByHashCode(userReq.getHashCode()));
            }else {
                user.setAttachment(null);
            }
            user.setFirstName(userReq.getFirstName());
            user.setLastName(userReq.getLastName());
            if (userReq.getAccountNonLocked()!=null) {
                user.setAccountNonLocked(userReq.getAccountNonLocked());
            }
            userRepository.save(user);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

//    @Override
//    public List<User> findAllOnlyUsers() {
//        try {
//            return userRepository.findAllOnlyUsers();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        return new ArrayList<>();
//    }

    @Override
    public Page<User> findAll(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size);
            return userRepository.findAllOnlyUsers(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Result delete(Long id) {
        try {
            if (id!=1) {
                userRepository.deleteById(id);
                return new Result(true, "deleting successful");
            }else{
                return new Result(false, "deleting failed");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public Result editPassword(ProfilSettings profilSettings, User user) {
        try {
            if (signin(new LoginReq(user.getUsername(), profilSettings.getOldPassword())).getBody().getToken()!=null){
                user.setPassword(passwordEncoder.encode(profilSettings.getNewPassword()));
                userRepository.save(user);
                return new Result(true, "repassword successful");
            }else {
                return new Result(false,"user not found");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"repassword failed");
    }

    @Override
    public Result editPasswordClient(UserPasswordReq userPasswordReq, Long userId) {
        try{
            User user=userRepository.findById(userId).get();
            if (Objects.requireNonNull(signin(new LoginReq(user.getUsername(), userPasswordReq.getOldPassword())).getBody()).getToken()!=null){
                user.setPassword(passwordEncoder.encode(userPasswordReq.getNewPassword()));
                userRepository.save(user);
                return new Result(true, "Successfully changed password!");
            }else {
                return new Result(false,"Password not verified.");
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Payment> getAllPaymentCourseByUserId(Long userId) {
        try {
            return paymentRepository.findAllByUserId(userId);
        }catch (Exception e){
            return null;
        }
    }
}
