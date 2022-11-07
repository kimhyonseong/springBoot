package com.example.foodpreference.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
//public class CustomAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(
//            HttpServletRequest request, HttpServletResponse response,
//            AuthenticationException exception) throws IOException, ServletException {
//        String errorMsg;
//
//        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
//            errorMsg = "아이디 또는 비밀번호를 확인해주세요";
//        } else if(exception instanceof UsernameNotFoundException) {
//            errorMsg = "존재하지 않는 아이디입니다.";
//        } else {
//            errorMsg = "알 수 없는 오류가 발생하였습니다.";
//        }
//
//        request.setAttribute("errorMsg",errorMsg);
//
//        setDefaultFailureUrl("/login");
//        super.onAuthenticationFailure(request, response, exception);
//    }
//}

public class CustomAuthFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String errorMsg;

        if(exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호를 확인해주세요";
        } else if(exception instanceof UsernameNotFoundException) {
            errorMsg = "존재하지 않는 아이디입니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "시스템 문제가 발생하였습니다. 관리자에게 문의하세요.";
        }else {
            errorMsg = "알 수 없는 오류가 발생하였습니다.";
        }

        request.setAttribute("errorMsg",errorMsg);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
    }
}
