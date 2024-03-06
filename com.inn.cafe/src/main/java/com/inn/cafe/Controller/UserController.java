package com.inn.cafe.Controller;
import com.inn.cafe.service.UserService;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.validation.Valid;
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

    @PutMapping("updateStatus")
    ResponseEntity<String>updateStatus(@RequestBody() Map<String,String>request){
        System.out.println(request);

        return  service.updateSatus(request);
    }

    @GetMapping("/logout")
    public String logout() {

        System.out.println("Callllllllllllllll");
        return "Logout";
    }



    @GetMapping("/current-user")

    public ResponseEntity<String> currentUser(Principal principal){
        return new  ResponseEntity<>(principal.getName(),HttpStatus.OK);

    }


    @GetMapping("checktoken")

    public  ResponseEntity<String>checkToken(){
        return  service.checkToken();
    }


    @PostMapping("/changepssword")
    public  ResponseEntity<String>changePassword(@RequestBody Map<String,String>requestMap){

        return  service.changePassword(requestMap);
    }

    @PostMapping("forgotPassword")

    public  ResponseEntity<String>forgotpassword(@Valid @RequestBody Map<String,String>requestMap){
        return service.forgotPassword(requestMap);
    }
}
