/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.foilen.login.api.LoginClient;
import com.foilen.login.api.to.FoilenLoginToken;
import com.foilen.login.spring.client.security.cookie.FoilenLoginCookieService;
import com.google.common.base.Strings;

public class FoilenLoginFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(FoilenLoginFilter.class);

    private FoilenLoginCookieService foilenLoginCookieService;

    private LoginClient loginClient;

    private UserDetailsService userDetailsService;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isHttpOnly = "https".equals(request.getScheme());

        // Already logged in
        String userId = foilenLoginCookieService.getLoggedInUser(httpServletRequest);
        if (userId != null) {
            logger.debug("User {} already logged in", userId);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            SecurityContextHolder.getContext().setAuthentication(new FoilenAuthentication(userDetails));

            chain.doFilter(request, response);
            return;
        }

        // Check if coming back from SSO login service
        String loginToken = foilenLoginCookieService.getLogInToken(httpServletRequest);
        if (!Strings.isNullOrEmpty(loginToken)) {
            userId = loginClient.findUserIdByToken(loginToken);
            if (!Strings.isNullOrEmpty(userId)) {
                logger.debug("User {} just logged in", userId);
                foilenLoginCookieService.setLoggedInUser(userId, httpServletResponse, isHttpOnly);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                SecurityContextHolder.getContext().setAuthentication(new FoilenAuthentication(userDetails));

                chain.doFilter(request, response);
                return;
            }
        }

        // Redirect to SSO
        FoilenLoginToken foilenLoginToken = loginClient.createToken(getFullUrl(httpServletRequest));
        foilenLoginCookieService.setLogInToken(foilenLoginToken.getToken(), httpServletResponse, isHttpOnly);
        try {
            httpServletResponse.sendRedirect(foilenLoginToken.getLoginUrl());
        } catch (IOException e) {
            logger.error("Problem redirecting", e);
        }
    }

    public FoilenLoginCookieService getFoilenLoginCookieService() {
        return foilenLoginCookieService;
    }

    private String getFullUrl(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder(httpServletRequest.getRequestURL());

        if (!Strings.isNullOrEmpty(httpServletRequest.getQueryString())) {
            sb.append("?");
            sb.append(httpServletRequest.getQueryString());
        }

        String fullurl = sb.toString();

        // Check if proxied
        String proto = httpServletRequest.getHeader("X-Forwarded-Proto");
        if (!Strings.isNullOrEmpty(proto)) {
            fullurl = proto + fullurl.substring(fullurl.indexOf(':'));
        }
        return fullurl;
    }

    public LoginClient getLoginClient() {
        return loginClient;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void setFoilenLoginCookieService(FoilenLoginCookieService foilenLoginCookieService) {
        this.foilenLoginCookieService = foilenLoginCookieService;
    }

    public void setLoginClient(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
