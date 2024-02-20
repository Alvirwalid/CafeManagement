package com.inn.cafe.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTSUtils {

    private  String secret="btechalvi";

    public  String extractUsername(String token){

        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();   //        extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){

        return  extractClaims(token,Claims::getExpiration);
    }

    public  <T>T extractClaims(String token, Function<Claims,T>claimsResolve){

        final  Claims claims= extractAllClaims(token);
        return  claimsResolve.apply(claims);

    }

    public  String generateToken(String userbname,String role){

        Map<String,Object>claims=new HashMap<>();

        claims.put("role",role);

        return  createToken(claims,userbname);
    }


    private String  createToken(Map<String,Object>claims,String subject){


        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }


    /// getAllDataFromToken
    public  Claims extractAllClaims(String token){
        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private  Boolean isTokenExpired(String token){

        return  extractExpiration(token).before(new Date());
    }


    public  Boolean validateToken(String toekn, UserDetails userDetails){
        return  (extractUsername(toekn).equals(userDetails.getUsername()) && !isTokenExpired(toekn));
    }

}
