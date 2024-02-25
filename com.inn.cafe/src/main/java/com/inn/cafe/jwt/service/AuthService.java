package com.inn.cafe.jwt.service;


import com.inn.cafe.POJO.AuthenticationResponse;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
public class AuthService {

    private  final UserRepository repo;

    private  final PasswordEncoder passwordEncoder;

    private  final  JwtService jwtService ;

    private  final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<String>register(User request){

        log.info("Inside signup{}",request);
//        repo.save(getUserFromRequest(request));

       try{


          boolean isPresence = repo.findByUsername(request.getUsername()).isPresent();

           if(!isPresence){

               repo.save(getUserFromRequest(request));

               return CafeUtils.getResponseEntity("Successfully register", HttpStatus.OK) ;


           }else {


               return CafeUtils.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST) ;
           }
       }catch (Exception e){

           e.printStackTrace();
       }


       return  CafeUtils.getResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);



    }



    public  AuthenticationResponse login(User request){


//        System.out.printf("Loginnnnnnnn "+request.getUsername(),request.getPassword());


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user=repo.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);

        return new   AuthenticationResponse(token);
    }



        private  boolean validateSignUp(User user){


        if(!user.getName().isBlank() && !user.getContactNumber().isBlank() && !user.getUsername().isBlank() && !user.getRole().toString().isBlank()
        && !user.getPassword().isBlank()){


            if(!user.getName().isEmpty()&& !user.getContactNumber().isEmpty() && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()
            && !user.getRole().toString().isEmpty()){

                return  true;

            }


        }
        return  false;
    }


    public User getUserFromRequest(User request){
        User user=new User();

        user.setName(request.getName());
        user.setContactNumber(request.getContactNumber());

        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(request.getStatus());
        user.setRole(request.getRole());
        jwtService.generateToken(user);

        return  user;

    }
















}
