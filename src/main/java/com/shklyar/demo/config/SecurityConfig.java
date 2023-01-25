package com.shklyar.demo.config;

import com.shklyar.demo.filter.JwtRequestFilter;
import com.shklyar.demo.security.jwt.JwtConfigurer;
import com.shklyar.demo.security.jwt.JwtTokenProvider;
import com.shklyar.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";

    private  static final String LOGIN_ENDPOINT = "/api/v1/auth/**";

   @Autowired
   public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
      this.jwtRequestFilter = jwtRequestFilter;
   }

   final private JwtRequestFilter jwtRequestFilter;


   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.cors().and().csrf()
              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
              .disable()
              .authorizeRequests()
              .antMatchers(LOGIN_ENDPOINT).permitAll()
              .antMatchers("/product/**").permitAll()
              .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
              .anyRequest().permitAll()


              .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
   }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        String[] allowDomains = new String[2];
        allowDomains[0] = "http://localhost:4200";
        allowDomains[1] = "http://localhost:8082";

        System.out.println("CORS configuration....");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(allowDomains);
            }
        };
    }
}
