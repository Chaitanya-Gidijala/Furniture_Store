package com.foryou.webapp.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.foryou.webapp.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                        		.requestMatchers("/furniture-store/product/upload","/products/cart-items").hasRole("ADMIN")
                        		
                        		.requestMatchers("/products/cart-items").hasRole("USER")
                                .requestMatchers("/home","/products/cart-items").hasRole("User")

                    		    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                    		    .requestMatchers("/","/home","/navigation","/index","/product/{id}","/products/cart-items").permitAll()
//                                .requestMatchers("/leaders").hasRole("MANAGER")
                                
                                
//                                .requestMatchers("/leaders/**").hasAnyRole("MANAGER","EMPLOYEE")
//                                .requestMatchers("/systems/**").hasRole("ADMIN")
//                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/home",true) 
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );
        

        return http.build();
    }
    

}