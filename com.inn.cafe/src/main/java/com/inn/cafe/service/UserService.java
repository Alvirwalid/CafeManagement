package com.inn.cafe.service;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface UserService {




ResponseEntity<List<UserWrapper>>getAllUser();



ResponseEntity<String>updateSatus(Map<String,String>request);




}
