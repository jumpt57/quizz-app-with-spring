package com.jumpt57.quizz.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenHelper tokenHelper;

    @Autowired
    public CustomAuthenticationSuccessHandler(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String jws = tokenHelper.generateToken(userDetails.getUsername());

        final Cookie cookie = new Cookie("token", jws);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        response.sendRedirect("/users");
    }
}
