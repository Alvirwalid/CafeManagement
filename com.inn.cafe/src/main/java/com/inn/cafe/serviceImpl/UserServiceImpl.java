package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.jwt.service.JwtService;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import io.jsonwebtoken.Jws;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    UserRepository repo;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    JwtService jwtService;


    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
//        System.out.printf(repo.getAllUser().toString());
//        return  new ResponseEntity<List<UserWrapper>>(repo.getAllUser(),HttpStatus.OK);
        try {
            if(jwtFilter.isAdmin()){
                return  new ResponseEntity<List<UserWrapper>>(repo.getAllUser(),HttpStatus.OK);

            }else {
                return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
