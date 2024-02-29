package com.inn.cafe.repository;

import com.inn.cafe.POJO.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {

//        @Query(
//"""
//select  t.id,t.token,t.loggedOut from  Token  t inner join User u on t.user.id=u.id where  t.user.id=:user_Id and  t.loggedOut=false
//
//"""
//            )
    String tokenQuery="select  t.* "
            + "from  token  t "
            + "inner join user u on t.user_id=u.id "
            + "where  t.user_id=%:user_Id% "
            + "and  t.is_logged_out=false  ";
    @Query(value =tokenQuery ,nativeQuery = true)
    List<Token> findAllTokenByUserId(@Param("user_Id") Integer user_Id);
    Optional<Token>findByToken(String token);
}
