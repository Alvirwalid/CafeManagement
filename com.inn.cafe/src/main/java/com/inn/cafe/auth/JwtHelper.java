package com.inn.cafe.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

    public  static  final  long JWT_TOKEN_VALIDITY=5*60*60;

    private  String secret="dfsd_hgr";


    //for retrieveing any information from token we will need the secret key


    private Claims getAllClaimsFromToken(String token){


//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
