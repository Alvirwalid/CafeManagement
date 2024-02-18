package com.inn.cafe.service;

import com.inn.cafe.POJO.User;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;



public interface UserService {




ResponseEntity<String>signUp(Map<String,String>requestMap);

ResponseEntity<List<User>>getAllUser();


}
