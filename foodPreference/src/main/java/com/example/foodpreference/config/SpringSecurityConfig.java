package com.example.foodpreference.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  private final UserDetailsService userDetailsService;
  private final CustomAuthFailHandler customAuthFailHandler;

  // spring security 필터를 타지 않게 무시
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/main","/resources/**","/h2-console/**");
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//    http.authorizeRequests().antMatchers("/**").permitAll()
//            .and()
//            .csrf().ignoringAntMatchers("/h2-console/**")
//            .and()
//            .headers()
//            .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
//            ));
//    http.csrf().ignoringAntMatchers("/resources/**","/login","/signup").and().authorizeRequests()

    http.csrf().disable().authorizeRequests()
              .antMatchers("/auth","/login","/loginProc","/signup","/main","/resources/**").permitAll()
              .antMatchers("/admin").hasRole("ADMIN")
              .antMatchers("/my").authenticated()
            .and()
              .formLogin()
              .loginPage("/login")
              .loginProcessingUrl("/login")
              .usernameParameter("id")
              .passwordParameter("pw")
              .defaultSuccessUrl("/main", false)
            .failureHandler(customAuthFailHandler)
            .and()
            .logout()
            .and()
            .rememberMe();
            //.logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc")) -> 생략시 default /logout

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
