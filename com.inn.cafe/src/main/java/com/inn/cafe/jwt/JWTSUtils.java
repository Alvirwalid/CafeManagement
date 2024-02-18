package com.inn.cafe.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JWTSUtils {
    private  String secret="alvi";

//    public Claims extractAllClaims(String token){
//
//        Jws<Claims> jwsClaims = Jwts.parser()
//                .requireIssuer("mycompany")
//                .require("admin", "true")
//                .setSigningKey(secret)
//                .parseClaimsJws(token);
//
//
//        return  Jwts.parser()
//                .requireIssuer("mycompany")
//                .require("admin", "true")
//                .setSigningKey(secret)
//                .parseClaimsJws(token);;
//    }
}
