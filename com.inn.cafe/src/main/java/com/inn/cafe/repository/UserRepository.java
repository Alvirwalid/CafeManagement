package com.inn.cafe.repository;

import com.inn.cafe.POJO.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


   Optional<User> findByUsername(@Param("username")String username);

   List<UserWrapper> getAllUser();
   List<UserWrapper> getAllAdmin();
//    Optional<User> findByUsername(String username);
}
