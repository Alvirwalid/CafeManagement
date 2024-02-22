package com.inn.cafe.jwt.controller;

import com.inn.cafe.POJO.AuthenticationResponse;
import com.inn.cafe.POJO.User;
import com.inn.cafe.jwt.service.AuthenticationService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Data
@RestController
public class AuthController {

 private   AuthenticationService authenticationService;


 @PostMapping("/register")
   public ResponseEntity<AuthenticationResponse>register(@RequestBody User request){
       return  ResponseEntity.ok(authenticationService.register(request));

   }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody User request){
        return  ResponseEntity.ok(authenticationService.authenticate(request));

    }
}
