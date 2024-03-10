package com.inn.cafe.service;
import com.inn.cafe.constant.BaseConstant;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService  extends BaseConstant {




ResponseEntity<BaseResponse>getAllUser();



ResponseEntity<BaseResponse> updateStatus(Map<String,String>request);
ResponseEntity<String>checkToken();
ResponseEntity<BaseResponse>changePassword(Map<String,String>requestMap);

ResponseEntity<BaseResponse>forgotPassword(Map<String,String>request);





}
