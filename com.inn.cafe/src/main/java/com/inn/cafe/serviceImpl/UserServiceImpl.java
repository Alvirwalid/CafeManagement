package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailSenderUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;



@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


   private final UserRepository repo;
    @Autowired
    private final JwtFilter jwtFilter;
    @Autowired
   private final EmailSenderUtils emailSenderUtils;
//    @Autowired
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;



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
                System.out.println("Adminnnnn");
        Optional<User> user=   repo.findById(Integer.parseInt( request.get("id")));



        if(user.isPresent()){

            sendToMailAllAdmin(request.get("status"),"alviarash620@gmail.com",repo.getAllAdmin());


            repo.updateStatus(request.get("status"),Integer.parseInt(request.get("id")));

        }else {
            return CafeUtils.getResponseEntity("User id not found",HttpStatus.OK);
        }

                return CafeUtils.getResponseEntity("Successfully update",HttpStatus.OK);

            }else {

                System.out.println("Not Admin");
                return  CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
           e.printStackTrace();
        }
          return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {

        try{}catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String,String>requestMap) {
        try{
//         User userObj=   repo.findByUsername(requestMap.get("username")).orElseThrow(); // after logout
         User userObj=   repo.findByUsername(jwtFilter.getCurrentUsername()).orElseThrow();

         if(!userObj.equals(null)){
             if(this.bCryptPasswordEncoder.matches(requestMap.get("oldPassword"),userObj.getPassword())){
                 userObj.setPassword(this.bCryptPasswordEncoder.encode(requestMap.get("newPassword")));
                 repo.save(userObj);
                 return  CafeUtils.getResponseEntity("Password updated successfully", HttpStatus.OK);
             };
             return  CafeUtils.getResponseEntity("Invalid old Password", HttpStatus.BAD_REQUEST);

         }

            return  CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> request) {

        try {
            User user = repo.findByUsername(request.get("username")).orElseThrow();
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getUsername())){
                emailSenderUtils.forgotMail(user.getUsername(),"Credentials by cafe management system",user.getPassword());
                return  CafeUtils.getResponseEntity("Check yor email for credentials",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void   sendToMailAllAdmin(String status,String username,List<UserWrapper>allAdmin){

        List<UserWrapper> l= allAdmin.stream().filter(userWrapper -> userWrapper.getUsername().equalsIgnoreCase(jwtFilter.getCurrentUsername())).toList();
        allAdmin.remove(l.get(0));

        List<String>ccList=new ArrayList<>();

        for(UserWrapper userWrapper:allAdmin){
            ccList.add(userWrapper.getUsername());
        }

//        for (int i=0;i<allAdmin.size();i++){
//
//            ccList.add(allAdmin.get(i).getUsername());
//        }

//        System.out.println("CC LIST "+ccList.size());

        if(status !=null && status.equalsIgnoreCase("true")){

//            System.out.println("Send gmail");

            emailSenderUtils.sendEmail(jwtFilter.getCurrentUsername(),"Account Approved","User:- "+username +"\n is approve by \n Admin",ccList);
        }
    }

}
