package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.BaseResponse;
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
    private  final  CafeUtils cafeUtils;
    @Override
    public ResponseEntity<BaseResponse> getAllUser() {

        try {
            if(jwtFilter.isAdmin()){
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getAllAdmin(),"",""),HttpStatus.OK);
            }else if (jwtFilter.isUser()){

                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getAllUser(),"",""),HttpStatus.OK);
            }else {
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(new ArrayList<>(),UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> updateStatus(Map<String,String>request) {

        try {
            if(jwtFilter.isAdmin()){
//                System.out.println("Adminnnnn");
                 Optional<User> user=   repo.findByUsername(request.get("username"));

                 if(user.isPresent()){
                    sendToMailAllAdmin(request.get("status"),"alviarash620@gmail.com",repo.getAllAdmin());

                    repo.updateStatus(request.get("status"),user.get().getId());


                     return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, UPDATE_MESSAGE,UPDATE_MESSAGE_BN),HttpStatus.OK);

               }else {
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.BAD_REQUEST);
                }


            }else {

                System.out.println("Not Admin");
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> checkToken() {

        try{}catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> changePassword(Map<String,String>requestMap) {
        try{
//         User userObj=   repo.findByUsername(requestMap.get("username")).orElseThrow(); // after logout

           Optional<User> userObj=   repo.findByUsername(jwtFilter.getCurrentUsername());

         if(userObj.isPresent()){

             if(this.bCryptPasswordEncoder.matches(requestMap.get("oldPassword"),userObj.get().getPassword())){

                 userObj.get().setPassword(this.bCryptPasswordEncoder.encode(requestMap.get("newPassword")));
                 repo.save(userObj.get());
                 return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, UPDATE_MESSAGE,UPDATE_MESSAGE_BN), HttpStatus.OK);
             }
             return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, "Invalid old Password",""), HttpStatus.BAD_REQUEST);

         }
            return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, "User Not Found",""), HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> forgotPassword(Map<String, String> request) {

        try {
            Optional<User> user = repo.findByUsername(request.get("username"));
            if(user.isPresent()){
                System.out.println("User Found");

                emailSenderUtils.forgotMail(user.get().getUsername(),"Credentials by cafe management system",user.get().getPassword());

                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,"Check yor email for credentials",""),HttpStatus.OK);
            }else {
                System.out.println("User not Found");
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,"User Not Found",""),HttpStatus.OK);
            }
        }catch (Exception e){
            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.OK);
        }

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
