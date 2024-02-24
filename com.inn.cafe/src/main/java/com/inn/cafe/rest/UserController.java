//package com.inn.cafe.rest;
//
//
//import com.inn.cafe.POJO.User;
//import com.inn.cafe.service.UserService;
//import com.inn.cafe.utils.CafeUtils;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/user")
//public class UserController {
//
//    private   UserService service;
//// password :   a1e9f20c-f050-4a2e-a12f-e5e8766480f1
//
//    @PostMapping("/signup")
//    public ResponseEntity<String>signUp(@RequestBody(required = true)User user){
//
//        try{
//            return  service.signUp(user);
//
//        }catch (Exception e){
//            return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @GetMapping("/all")
//
//    public ResponseEntity<List<User>> getUser(){
//        return service.getAllUser();
//
//    }
//
//    @GetMapping("/current-user")
//
//    public ResponseEntity<String> currentUser(Principal principal){
//        return new  ResponseEntity(principal.getName(),HttpStatus.OK);
//
//    }
//}
