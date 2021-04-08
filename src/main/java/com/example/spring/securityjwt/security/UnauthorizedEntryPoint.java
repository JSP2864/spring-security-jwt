package com.example.spring.securityjwt.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
        final String expired = (String) httpServletRequest.getAttribute("expired");
        System.out.println(expired);
        if(expired !=null){
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"TOKEN_EXPIRED");
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorised");
        }
    }
}
