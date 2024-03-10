package com.inn.cafe.jwt.controller;


import com.inn.cafe.POJO.auth.AuthenticationResponse;
import com.inn.cafe.POJO.User;
import com.inn.cafe.jwt.service.AuthService;
import com.inn.cafe.repository.TokenRepository;
import com.inn.cafe.request.LoginRequest;
import com.inn.cafe.request.SignupRequest;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    TokenRepository tokenRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signUp(@RequestBody() SignupRequest user){

        return  authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }



//    @GetMapping("/tokens")
//
//    public  ResponseEntity<List<Token>>getAllTokenByUserId(@PathVariable("user_id") Integer userId){
//        return  new ResponseEntity<>(tokenRepository.findAllTokenByUser(userId),HttpStatus.OK);
//    }


}
