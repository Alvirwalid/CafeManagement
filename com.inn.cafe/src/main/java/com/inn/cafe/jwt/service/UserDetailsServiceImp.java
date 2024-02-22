package com.inn.cafe.jwt.service;


import com.inn.cafe.repository.UserRepository;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserDetailsServiceImp  implements UserDetailsService {
   @Autowired
    public  final  UserRepository userRepository;

    private  com.inn.cafe.POJO.User userDetail;

    public UserDetailsServiceImp(UserRepository repository) {
        this.userRepository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail=userRepository.findByEmailId(username);


        if(Objects.isNull(userDetail))
            return  new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());


         else
             throw  new UsernameNotFoundException("User not found");
    }
}
