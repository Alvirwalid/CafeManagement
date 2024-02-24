package com.inn.cafe.repository;

import com.inn.cafe.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


   Optional<User> findByUsername(@Param("username")String username);
//    Optional<User> findByUsername(String username);
}
