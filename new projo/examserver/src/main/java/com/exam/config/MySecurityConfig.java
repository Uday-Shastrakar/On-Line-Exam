package com.exam.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.AbstractDaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exam.service.ipmpl.UserDetailsServiceImpl;
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig  {
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
  @Autowired
	private UserDetailsServiceImpl userDetailServiceimpl;
 
  @Bean
   public AuthenticationManager authenticationManager( 
	AuthenticationConfiguration authConfig)throws Exception{
    return authConfig.getAuthenticationManager();
}
  
  //for testing purpose(do not use this code it is not secure)
//  @Bean
//     public PasswordEncoder passwordEncoder() {
//	 return NoOpPasswordEncoder.getInstance();
//	  
//  }
  
//  actual  code
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	  
  }
 
  
  protected void configure(AuthenticationManagerBuilder auth)throws Exception{
	  auth.userDetailsService(this.userDetailServiceimpl).passwordEncoder(passwordEncoder());
  }
  
  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
	
     http
     
           .csrf()
           .disable()
           .cors()
           .disable()
           .authorizeRequests()
           .antMatchers(  "/generate-token","/user/","/user/test").permitAll()
           .antMatchers(HttpMethod.OPTIONS).permitAll()
           .anyRequest().authenticated()
           .and()
           .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
           .and()
           .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
     
       http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);    
	return http.build();
	
		
	}
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return null;
		
	}
}
