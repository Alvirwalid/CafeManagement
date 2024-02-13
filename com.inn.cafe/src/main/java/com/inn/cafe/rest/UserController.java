package com.inn.cafe.rest;


import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private   UserService service;


    @PostMapping("/signup")
    public ResponseEntity<String>signUp(@RequestBody(required = true)Map<String,String>requestMap){

        try{
            return  service.signUp(requestMap);
        }catch (Exception e){
            return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
