package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.jwt.service.JwtService;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


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

        try {
            if(jwtFilter.isAdmin()){

                return  new ResponseEntity<List<UserWrapper>>(repo.getAllAdmin(),HttpStatus.OK);
            }else if (jwtFilter.isUser()){

                return  new ResponseEntity<List<UserWrapper>>(repo.getAllUser(),HttpStatus.OK);
            }else {
                return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateSatus(Map<String,String>request) {




        try {
            if(jwtFilter.isAdmin()){

        Optional<User> user=   repo.findById(Integer.parseInt( request.get("id")));


        if(user.isPresent()){
            repo.updateStatus(request.get("status"),Integer.parseInt(request.get("id")));

        }else {
            return CafeUtils.getResponseEntity("User id not found",HttpStatus.OK);
        }


//

                return CafeUtils.getResponseEntity("Successfully update",HttpStatus.UNAUTHORIZED);



            }else {
                return  CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
           e.printStackTrace();
        }
          return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
