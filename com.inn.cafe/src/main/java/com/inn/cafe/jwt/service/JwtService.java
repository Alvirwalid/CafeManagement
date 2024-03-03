package com.inn.cafe.jwt.service;


import com.inn.cafe.POJO.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private  final  String SECRET_KEY="a94cfea912ae936a59f2c8bf553f28ac47e1b85301b09e3157fa0034914fe793";


    public  String extractUsername(String token){

        Claims claims=Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    public  String extractRole(String token){

        Claims claims=Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (String) claims.get("role");
    }



    public  boolean isValid(String token, UserDetails user){

        String username=extractUsername(token);
        return  (username.equals(user.getUsername())) && isTokenExpired(token);
    }
    private  boolean isTokenExpired(String token){


        return extractExpirationDate(token).before(new Date());
    }

    private  Date extractExpirationDate(String token){
        return  extractClaims(token,Claims::getExpiration);
    }





    public  <T>T extractClaims(String token, Function<Claims,T> resolve){

        Claims claims=extractAllClaims(token);

        return  resolve.apply(claims);

    }


    public Claims extractAllClaims(String token){


//        System.out.println("extractAllClaims");
//        System.out.println("extractAllClaims"+Jwts.parser()
//                .verifyWith(getSigninKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload());
//
//       boolean u= isUser(token);
//       boolean a= isAdmin(token);
//
//        System.out.println("User ::"+u);
//        System.out.println("Admin ::"+a);
//
//        String name=getCurrentUsername(token);
//
//
//        System.out.println("User ::"+u);
//        System.out.println("Admin ::"+a);
//        System.out.println("Name ::"+name);
        return  Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public  String generateToken(String username,String role){

        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role);


        return createToken(claims,username);
    }

    public  String createToken( Map<String,Object> claims,String subject){

        String token= Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigninKey())
                .compact();





        return  token;

    }


    private SecretKey getSigninKey(){


        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public  String getCurrentUsername(String token){
        return  extractUsername(token);
    }


}
