package com.app.excel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.excel.security.JWTAuthenticationFilter;

import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor 

public class SecurityConfig  {
	
	@Autowired 
	private  JWTAuthenticationFilter jwtFilter;
	
	/**@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	        .build();
	} **/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
	        		//.requestMatchers("/api/admin/dashboard").hasRole("ADMIN") 
	        		.requestMatchers("/api/admin-dashboard/").hasRole("ADMIN") 
	        		.requestMatchers("/api/admin/**").hasRole("ADMIN") 
	        		.requestMatchers("/api/sales/").hasRole("ADMIN")
	        		.requestMatchers("/api/purchase/").hasRole("ADMIN")
	        		.requestMatchers("/api/analyst-dashboard/").hasAnyRole("ANALYST") 
	        		.requestMatchers("/api/analyst/").hasAnyRole("ANALYST") 
	        		.requestMatchers("/api/excel/upload/").hasAnyRole("ANALYST") 
	        		.requestMatchers("/api/public/**").permitAll() 
	        		.requestMatchers("/api/auth/login", 
	        				"/api/auth/register", 
	        				"/api/auth/logout",
	        				"/api/admin/dashboard").permitAll()
	        		.anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	        .build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
