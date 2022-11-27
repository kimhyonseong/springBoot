package com.example.foodpreference.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  private final UserDetailsService userDetailsService;
  private final CustomAuthFailHandler customAuthFailHandler;
  private final CustomAuthSuccessHandler customAuthSuccessHandler;

  // spring security 필터를 타지 않게 무시
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/resources/**","/h2-console/**");
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeRequests()
//            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .antMatchers("/admin/**").hasRole("ADMIN")  // 비교하는 값에는 'ROLE_'로 시작해야함
            .antMatchers("/item/buy").hasAnyRole("ADMIN","USER")
//            .antMatchers("/**").permitAll()
            .and()
            .csrf().ignoringAntMatchers("/h2-console/**","/admin/**","/itemRest/**")
            .ignoringAntMatchers("/addCart")
            .and()
            .headers()
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
            ))
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("id")
            .passwordParameter("pw")
            .defaultSuccessUrl("/main",false)
            .successHandler(customAuthSuccessHandler)
            .failureHandler(customAuthFailHandler)
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 없으면 에러 -> default 아닌가봄
            .logoutSuccessUrl("/main")
            .invalidateHttpSession(true);
//    http.csrf().ignoringAntMatchers("/resources/**","/login","/signup").and().authorizeRequests()

//    http.csrf().disable().authorizeRequests()
//              .antMatchers("/auth","/login","/loginProc","/signup","/main","/resources/**").permitAll()
//            .rememberMe();

    return http.build();
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
