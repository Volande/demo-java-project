package com.shklyar.demo.config;

import com.shklyar.demo.filter.JwtRequestFilter;
import com.shklyar.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
      this.jwtRequestFilter = jwtRequestFilter;
   }

   private JwtRequestFilter jwtRequestFilter;

  /* @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(UserServiceImpl);
   }
*/

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.cors().and().csrf()
              .disable()
              .authorizeRequests()
              .antMatchers("/admin/**").authenticated()
              .antMatchers("/user/**").authenticated()
              .antMatchers("/products/**").permitAll()
              .antMatchers("/v2/api-docs.").permitAll()
              .anyRequest().permitAll()
              .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
   }

   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

   @Bean
   public PasswordEncoder passwordEncoder() {
      return bCryptPasswordEncoder;
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.addAllowedHeader("*");
      configuration.addAllowedMethod("*");
      configuration.addAllowedOrigin("*");
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
}