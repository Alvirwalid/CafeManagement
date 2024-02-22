package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Role;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    UserRepository repo;


    @Transactional
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside signup{}",requestMap);

    try{
        if(validateSignUp(requestMap)){


            User user =repo.findByEmailId(requestMap.get("email"));
            if(Objects.isNull(user)){
                repo.save(getUserFromMap(requestMap));

                return  CafeUtils.getResponseEntity("Successfully Register.",HttpStatus.OK);

            }else{

                return  CafeUtils.getResponseEntity("Email Already Exist",HttpStatus.BAD_REQUEST);
            }

        }else {
            return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
    }catch (Exception e){


        e.printStackTrace();
    }

    return  CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @Transactional
    @Override
    public   ResponseEntity<List<User>>getAllUser(){

        List<User> users= repo.findAll();
        return new   ResponseEntity(users,HttpStatus.OK);
    }




    private  boolean validateSignUp(Map<String,String>requestMap){

      boolean isEmpty=  requestMap.get("name").isEmpty();
        System.out.println("EMpltyyyyyyyyyyyyyyyyyyyy"+isEmpty);

        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){


            if(!requestMap.get("name").isEmpty()&& !requestMap.get("contactNumber").isEmpty() && !requestMap.get("email").isEmpty() && !requestMap.get("password").isEmpty()){

                return  true;

            }


        }
        return  false;
    }

    private User getUserFromMap(Map<String,String>requestMap){
        User user=new User();

        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole(Role.valueOf(requestMap.get("admin")));


        return  user;


    }
}
