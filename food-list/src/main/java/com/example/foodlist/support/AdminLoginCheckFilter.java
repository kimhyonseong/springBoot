package com.example.foodlist.support;

import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/admin/*")
@RequiredArgsConstructor
public class AdminLoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter in");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String loginId = null;
        Cookie[] cookies = httpServletRequest.getCookies();

        try {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "loginId")) {
                    loginId = cookie.getValue();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("cookie is null");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (Objects.equals(loginId, "") || loginId == null) {
            System.out.println("로그인 안함");
            request.setAttribute("redirectUrl","/login");
            request.setAttribute("message","로그인이 필요합니다.");
            //layout/redirect
            RequestDispatcher dispatcher = request.getRequestDispatcher("/redirect");
            dispatcher.forward(request,response);
        }

        chain.doFilter(request,response);

        System.out.println("filter out");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
