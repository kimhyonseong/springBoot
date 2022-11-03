package com.example.foodpreference.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  private final UserDetailsService userDetailsService;
  //private final LoginIdPwValidator loginIdPwValidator;

  // spring security 필터를 타지 않게 무시
//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring().antMatchers("/resources/**","/h2-console/**");
//  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    http.csrf().disable().authorizeRequests()
              .antMatchers("/login","/signup","/main").permitAll()
              .antMatchers("/resources/**").permitAll()
              .antMatchers("/admin").hasRole("ADMIN")
              .antMatchers("/my").authenticated()
            .and()
              .formLogin()
              .loginPage("/login")
              .loginProcessingUrl("/login")
              .usernameParameter("id")
              .passwordParameter("pw")
              .defaultSuccessUrl("/main",false)
              .failureForwardUrl("/login")
            .and()
            .logout();
            //.logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc")) -> 생략시 default /logout

    return http.build();
  }
}
