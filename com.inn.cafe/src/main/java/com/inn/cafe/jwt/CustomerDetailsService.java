package com.inn.cafe.jwt;

import com.inn.cafe.POJO.User;
import com.inn.cafe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {


    @Autowired

    UserRepository repo;
    User userDetails;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Inside loadUserByUsername {}",username);
        userDetails = repo.findByEmailId(username);
        if(!Objects.isNull(userDetails))
            return new  org.springframework.security.core.userdetails.User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());

        else
            throw  new UsernameNotFoundException("User not found");
    }
}
