package com.inn.cafe.repository;

import com.inn.cafe.POJO.User;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


   Optional<User> findByUsername(@Param("username")String username);



   List<UserWrapper> getAllUser();
   List<UserWrapper> getAllAdmin();

String updateQuery= "update user u  " +
        "set u.status=%:status% " +
        "where u.id=%:id%";
   @Transactional
   @Modifying
   @Query(value =updateQuery,nativeQuery = true)
   Integer updateStatus(@Param("status") String status,@Param("id") Integer id);
//    Optional<User> findByUsername(String username);
}
