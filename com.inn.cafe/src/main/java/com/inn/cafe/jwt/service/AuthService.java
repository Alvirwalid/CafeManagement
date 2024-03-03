package com.inn.cafe.jwt.service;


import com.inn.cafe.POJO.auth.AuthenticationResponse;
import com.inn.cafe.POJO.User;
import com.inn.cafe.POJO.auth.Token;
import com.inn.cafe.repository.TokenRepository;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.utils.CafeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class AuthService {
    @Autowired
    private  final UserRepository repo;
    @Autowired
    private  final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  final  JwtService jwtService ;
    @Autowired
    private  final AuthenticationManager authenticationManager;
    @Autowired
    private  final TokenRepository tokenRepository;

//    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
//        this.repo = repo;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
//        this.tokenRepository = tokenRepository;
//    }

    public ResponseEntity<String>register(User request){
        log.info("Inside signup{}",request);
       try{
           if(!repo.findByUsername(request.getUsername()).isPresent()){

              User user= repo.save(getUserFromRequest(request));
              String jwt=jwtService.generateToken(user.getUsername(),user.getRole().toString());
              saveToken(jwt,user);
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        User user=repo.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user.getUsername(),user.getRole().toString());

        System.out.println("User ID : "+user.getId());

     List<Token> tokens=  tokenRepository.findAllTokenByUserId(user.getId());
//        for (Token t:tokens){
//            System.out.println(t.getToken());
//        }



        return new   AuthenticationResponse(token);
    }

    private  void  saveToken(String jwt,User user){
        Token token=new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);

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
        jwtService.generateToken(user.getUsername(),user.getRole().toString());

        return  user;

    }


















}
