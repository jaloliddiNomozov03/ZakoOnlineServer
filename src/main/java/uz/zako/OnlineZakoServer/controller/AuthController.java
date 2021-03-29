package uz.zako.OnlineZakoServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.zako.OnlineZakoServer.entity.User;
import uz.zako.OnlineZakoServer.model.JwtResponse;
import uz.zako.OnlineZakoServer.payloads.request.LoginReq;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;
import uz.zako.OnlineZakoServer.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private UserService userService;
    @Autowired
    public AuthController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginReq loginReq){
        return userService.signin(loginReq);
    }
    @GetMapping("/me")
    public UserReq getMet(Authentication authentication){
        return userService.getMe((User) authentication.getPrincipal());
    }
}
