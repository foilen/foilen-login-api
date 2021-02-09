/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring.client.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.foilen.login.spring.client.security.FoilenAuthentication;

public class FoilenLoginFilterStub implements Filter {

    private UserDetailsService userDetailsService;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Already logged in
        String userId = "111111";
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        SecurityContextHolder.getContext().setAuthentication(new FoilenAuthentication(userDetails));

        chain.doFilter(request, response);
        return;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
