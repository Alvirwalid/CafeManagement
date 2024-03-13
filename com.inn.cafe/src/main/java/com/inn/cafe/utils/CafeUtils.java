package com.inn.cafe.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.inn.cafe.POJO.auth.AuthenticationResponse;
import com.inn.cafe.constant.BaseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@AllArgsConstructor
public class CafeUtils implements BaseConstant {




    public  BaseResponse generateSuccessResponse(Object object , String ...message){
        BaseResponse res= new BaseResponse();

        res.setData(object);
        res.setStatus(true);

        System.out.println(message[0]);

        if(message.length >1 && message[0] !=null){
            res.setMessage(message[0]);
            res.setMessageBn(message[1]);
        }

        return  res;
    }

    public  BaseResponse generateErrorResponse(Exception e){
        BaseResponse res= new BaseResponse();
        res.setStatus(false);
        String messageType = getMasseageType(e.getMessage());

        if(messageType.equals("uk") || messageType.equals("re")){
            res.setMessage(DATA_ALRADY_EXISTS_MESSAGE);
            res.setMessageBn(DATA_ALRADY_EXISTS_MESSAGE_BN);
        }else  if (messageType.equals("fk")){
            res.setMessage(CHILD_RECORD_FOUND);
            res.setMessageBn(CHILD_RECORD_FOUND_BN);

        }
        else {
            res.setMessage(e.getMessage());
        }

        return  res;


    }


    public  static ResponseEntity<String>getResponseEntity(String message, HttpStatus httpStatus){
        return new  ResponseEntity<String>("{\"message\":\""+message+"\"}",httpStatus);
    }
    public  static ResponseEntity<AuthenticationResponse>getResponseEntityForAuth(String message, HttpStatus httpStatus){
        return new  ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(message),httpStatus);
    }

    private String getMasseageType(String message){
        if(message !=null && message.length() >55){
            return  message.substring(52,54);
        }
        return "";
    }

    public  static  String getUUID(){
        Date date=new Date();
        long time=date.getTime();
        return "BILL"+time;
    }

    public  JSONArray getJsonArrayFromString(String data)throws JSONException {
        JSONArray jsonArray=new JSONArray(data);
        return  jsonArray;
    }

    public   Map<String,Object>getMapFromJson(String data){

        if(!data.isEmpty()){
            return new  Gson().fromJson(data, new TypeToken<Map<String,Object>>(){}.getType());
        }
        return  new HashMap<>();
    }
    public  Boolean isExistFile(String path){
        try {
            File file=new File(path);
            return (file !=null && file.exists())?Boolean.TRUE:Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

}
