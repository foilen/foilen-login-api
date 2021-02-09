/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

import com.foilen.login.api.LoginClientSpringConfig;
import com.foilen.login.spring.client.security.cookie.FoilenLoginCookieService;
import com.foilen.login.spring.client.security.cookie.FoilenLoginCookieServiceImpl;
import com.foilen.login.spring.services.FoilenLoginService;
import com.foilen.login.spring.services.FoilenLoginServiceImpl;

/**
 * Use this config to use the login system with Spring Security.
 */
@Configuration
@Import(LoginClientSpringConfig.class)
public class FoilenLoginSecurityConfig {

    @Autowired
    public Environment environment;

    @Bean
    public FoilenLoginCookieService foilenLoginCookieService() {
        FoilenLoginCookieServiceImpl foilenLoginCookieService = new FoilenLoginCookieServiceImpl();
        foilenLoginCookieService.setCookieSignatureSalt(environment.getRequiredProperty("login.cookieSignatureSalt"));
        return foilenLoginCookieService;
    }

    @Bean
    public FoilenLoginService foilenLoginService() {
        return new FoilenLoginServiceImpl();
    }

    @Bean
    public FoilenLoginUserDetailsService foilenLoginUserDetailsService() {
        return new FoilenLoginUserDetailsService();
    }

    @Bean
    public FoilenLoginWebSecurityConfigurerAdapter foilenLoginWebSecurityConfigurerAdapter() {
        return new FoilenLoginWebSecurityConfigurerAdapter();
    }

    @Bean
    public LoginClientSpringConfig loginClientConfig() {
        return new LoginClientSpringConfig();
    }

    @Bean
    public UserCache userCache() throws Exception {
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("users");
        return new SpringCacheBasedUserCache(concurrentMapCache);
    }

}
