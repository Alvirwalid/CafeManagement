package com.inn.cafe.service;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface UserService {




ResponseEntity<List<UserWrapper>>getAllUser();



ResponseEntity<String>updateSatus(Map<String,String>request);
ResponseEntity<String>checkToken();
ResponseEntity<String>changePassword(Map<String,String>requestMap);

ResponseEntity<String>forgotPassword(Map<String,String>request);





}
