package com.inn.cafe.jwt.config;


import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.jwt.service.UserDetailsServiceImp;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Data
@Configuration
@EnableWebSecurity
public class Config {


    private  final UserDetailsServiceImp userDetailsServiceImp;
    private  final JwtFilter jwtFilter;

    private  final  CustomLogoutHandler logoutHandler;

//    public Config(UserDetailsServiceImp userDetailsServiceImp, JwtFilter jwtFilter) {
//        this.userDetailsServiceImp = userDetailsServiceImp;
//        this.jwtFilter = jwtFilter;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/signup/**","/login/**","/tokens","/user/get","/user/updateStatus","/user/changepssword")
                                .permitAll()
                                .requestMatchers("/admin_only/**").hasAuthority("ADMIN")
                                .requestMatchers("/user_only/**").hasAuthority("USER")
                                .anyRequest()
                                .authenticated()

                        ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e->e.accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(403))
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(
                        l->l.logoutUrl("/logout").permitAll()
                        .addLogoutHandler(logoutHandler)
                         .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()).permitAll()
                       )
                        .securityContext(httpSecuritySecurityContextConfigurer -> httpSecuritySecurityContextConfigurer.requireExplicitSave(false))
                        .formLogin(AbstractHttpConfigurer::disable)
                        .build();
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
