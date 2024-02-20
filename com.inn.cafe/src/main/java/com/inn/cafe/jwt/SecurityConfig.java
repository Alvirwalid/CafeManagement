package com.inn.cafe.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    CustomerDetailsService customerDetailsService;

    @Autowired
    JwtFilter jwtFilter;




//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//       auth.userDetailsService(customerDetailsService);
//    }





//    @Bean (name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
     }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin();
//        http.cors().configurationSource(request->new CorsConfiguration().applyPermitDefaultValues())
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/login")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().exceptionHandling()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//
//        http.addFilterBefore((Filter) jwtFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);
//

        }
}
