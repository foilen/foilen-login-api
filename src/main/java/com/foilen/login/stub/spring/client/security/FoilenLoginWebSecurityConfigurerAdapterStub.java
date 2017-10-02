/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.foilen.smalltools.spring.security.CookiesGeneratedCsrfTokenRepository;
import com.foilen.smalltools.tools.SecureRandomTools;
import com.google.common.base.Strings;

public class FoilenLoginWebSecurityConfigurerAdapterStub extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${login.exclude:}")
    private String loginExcludes;

    @Autowired(required = false)
    private CsrfTokenRepository upstreamCsrfTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        FoilenLoginFilterStub foilenLoginFilter = new FoilenLoginFilterStub();
        foilenLoginFilter.setUserDetailsService(userDetailsService);

        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.addFilterBefore(foilenLoginFilter, AbstractPreAuthenticatedProcessingFilter.class);

        // Set the CSRF token
        CsrfTokenRepository csrfTokenRepository = upstreamCsrfTokenRepository;
        if (csrfTokenRepository == null) {
            csrfTokenRepository = new CookiesGeneratedCsrfTokenRepository().setSalt(SecureRandomTools.randomBase64String(10)).addCookieNames("foilen_user_id", "foilen_date", "foilen_signature");
        }
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
