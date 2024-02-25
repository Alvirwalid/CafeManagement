package com.inn.cafe.jwt.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControrller {


    @GetMapping("/admin_only")
    public ResponseEntity<String>getAdmin(){

        return new  ResponseEntity<String>("Hello Admin", HttpStatus.OK);
    }

    @GetMapping("/user_only")
    public ResponseEntity<String>getUser(){

        return new  ResponseEntity<String>("Hello User", HttpStatus.OK);
    }
}
