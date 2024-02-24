//package com.inn.cafe.serviceImpl;
//
//import com.inn.cafe.POJO.User;
//import com.inn.cafe.constant.CafeConstant;
//import com.inn.cafe.repository.UserRepository;
//import com.inn.cafe.service.UserService;
//import com.inn.cafe.utils.CafeUtils;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//
//@Slf4j
//@AllArgsConstructor
//@Service
//public class UserServiceImpl implements UserService {
//
//
//    UserRepository repo;
//
//
//    @Transactional
//    @Override
//    public ResponseEntity<String> signUp(User request) {
//
//        log.info("Inside signup{}",request);
//
//    try{
//        if(validateSignUp(request)){
//
//
//            User userDetail=repo.findByUsername(request.getUsername()).orElseThrow();
//
//            if(Objects.isNull(userDetail)){
//                repo.save(getUserFromMap(request));
//
//                return  CafeUtils.getResponseEntity("Successfully Register.",HttpStatus.OK);
//
//            }else{
//
//                return  CafeUtils.getResponseEntity("Email Already Exist",HttpStatus.BAD_REQUEST);
//            }
//
//        }else {
//            return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
//        }
//    }catch (Exception e){
//
//
//        e.printStackTrace();
//    }
//
//    return  CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//
//
//    @Transactional
//    @Override
//    public   ResponseEntity<List<User>>getAllUser(){
//
//        List<User> users= repo.findAll();
//        return new   ResponseEntity(users,HttpStatus.OK);
//    }
//
//
//
//
//    private  boolean validateSignUp(User user){
//
//      boolean isEmpty=  user.getName().isEmpty();
//        System.out.println("EMpltyyyyyyyyyyyyyyyyyyyy"+isEmpty);
//
//        if(!user.getName().isBlank() && !user.getContactNumber().isBlank() && !user.getUsername().isBlank() && !user.getRole().toString().isBlank()
//        && !user.getPassword().isBlank()){
//
//
//            if(!user.getName().isEmpty()&& !user.getContactNumber().isEmpty() && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()
//            && !user.getRole().toString().isEmpty()){
//
//                return  true;
//
//            }
//
//
//        }
//        return  false;
//    }
//
//    private User getUserFromMap(User user){
//        User user1=new User();
//
//        user.setName(user.getName());
//        user.setContactNumber(user.getContactNumber());
//        user.setUsername(user.getUsername());
//        user.setPassword(user.getPassword());
//        user.setStatus(user.getStatus());
//        user.setRole(user.getRole());
//
//
//        return  user;
//
//    }
//
//}
