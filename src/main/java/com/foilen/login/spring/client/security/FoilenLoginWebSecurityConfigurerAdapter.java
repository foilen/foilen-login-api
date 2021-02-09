/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.foilen.login.api.LoginClient;
import com.foilen.login.spring.client.security.cookie.FoilenLoginCookieService;
import com.google.common.base.Strings;

public class FoilenLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;
    @Autowired
    private FoilenLoginCookieService foilenLoginCookieService;
    @Autowired
    private LoginClient loginClient;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${login.exclude:}")
    private String loginExcludes;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        FoilenLoginFilter foilenLoginFilter = new FoilenLoginFilter();
        foilenLoginFilter.setFoilenLoginCookieService(foilenLoginCookieService);
        foilenLoginFilter.setLoginClient(loginClient);
        foilenLoginFilter.setUserDetailsService(userDetailsService);

        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.addFilterBefore(foilenLoginFilter, AbstractPreAuthenticatedProcessingFilter.class);

        // Set the CSRF token
        http.csrf().csrfTokenRepository(csrfTokenRepository);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if (!Strings.isNullOrEmpty(loginExcludes)) {
            for (String path : loginExcludes.split(",")) {
                web.ignoring().antMatchers(path);
            }
        }
    }

}
