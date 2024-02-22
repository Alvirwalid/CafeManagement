package com.inn.cafe.jwt.config;

import com.inn.cafe.POJO.Token;
import com.inn.cafe.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private  final TokenRepository tokenRepository;






    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String  authHeader = request.getHeader("Authorization");

        if(authHeader ==null || !authHeader.startsWith("Bearer ")){

            return ;

        }


        String token=authHeader.substring(7);
        Token storeToken=tokenRepository.findByToken(token).orElse(null);


        if(storeToken !=null){
            storeToken.setLogOut(true);
            tokenRepository.save(storeToken);
        }

    }
}
