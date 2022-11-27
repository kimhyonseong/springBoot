package com.example.foodpreference.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final RequestCache requestCache = new HttpSessionRequestCache();
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
  throws IOException {
    HttpSession session = request.getSession(false);

    if (session != null)
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

    SavedRequest savedRequest = requestCache.getRequest(request,response);

    String prePage = (String) request.getSession().getAttribute("prePage");
    if (prePage != null)
      request.getSession().removeAttribute("prePage");

    String url = "/";

    if (savedRequest != null) {
      url = savedRequest.getRedirectUrl();
    } else if (prePage != null && !prePage.equals("")){
      if (prePage.contains("/join")) {
        url = "/";
      } else{
        url = prePage;
      }
    }
    redirectStrategy.sendRedirect(request,response,url);
  }
}
