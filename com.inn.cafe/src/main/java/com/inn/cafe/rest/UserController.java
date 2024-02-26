package com.inn.cafe.rest;


import com.inn.cafe.POJO.User;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private   UserService service;


    @GetMapping("/get")
    ResponseEntity<List<UserWrapper>>getAllUser(){

        return  service.getAllUser();


    }






    @GetMapping("/current-user")

    public ResponseEntity<String> currentUser(Principal principal){
        return new  ResponseEntity(principal.getName(),HttpStatus.OK);

    }
}
