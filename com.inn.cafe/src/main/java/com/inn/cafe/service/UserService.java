package com.inn.cafe.service;

import com.inn.cafe.POJO.User;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;



public interface UserService {




ResponseEntity<List<UserWrapper>>getAllUser();


}
