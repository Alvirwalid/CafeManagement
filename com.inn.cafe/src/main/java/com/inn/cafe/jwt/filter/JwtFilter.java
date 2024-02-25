package com.inn.cafe.jwt.filter;


import com.inn.cafe.jwt.service.JwtService;
import com.inn.cafe.jwt.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private  final JwtService jwtService;

    private  final UserDetailsServiceImp userDetailsServiceImp;

    public JwtFilter(JwtService jwtService, UserDetailsServiceImp userDetailsServiceImp) {
        this.jwtService = jwtService;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull   HttpServletResponse response, @NotNull  FilterChain filterChain) throws ServletException, IOException {



        String authHeader=request.getHeader("Authorization");

        if(authHeader ==null || !authHeader.startsWith("Bearer")){

            filterChain.doFilter(request,response);
            return;
        }


        String token=authHeader.substring(7);
        String username= jwtService.extractUsername(token);

        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){


            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);


            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                    userDetails,null,  userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }



filterChain.doFilter(request,response);

    }
}
