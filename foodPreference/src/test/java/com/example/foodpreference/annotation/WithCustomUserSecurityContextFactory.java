package com.example.foodpreference.annotation;

import com.example.foodpreference.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {
  @Override
  public SecurityContext createSecurityContext(WithCustomUser annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    String userName = annotation.userName();
    String password = annotation.password();
    String role = annotation.role();

    Member member = new Member();
    member.setId(userName);
    member.setPassword(password);
    member.setRole(role);

    Authentication auth = new UsernamePasswordAuthenticationToken(member,member.getPassword(),member.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}
