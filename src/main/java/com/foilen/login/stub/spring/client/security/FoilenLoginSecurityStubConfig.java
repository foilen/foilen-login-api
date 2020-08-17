/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring.client.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.foilen.login.spring.services.FoilenLoginService;
import com.foilen.login.stub.spring.services.FoilenLoginServiceStub;

/**
 * Use this config to use a fake login system with Spring Security.
 */
@Configuration
public class FoilenLoginSecurityStubConfig {

    @Bean
    public FoilenLoginService foilenLoginService() {
        return new FoilenLoginServiceStub();
    }

    @Bean
    public FoilenLoginUserDetailsServiceStub foilenLoginUserDetailsServiceStub() {
        return new FoilenLoginUserDetailsServiceStub();
    }

    @Bean
    public FoilenLoginWebSecurityConfigurerAdapterStub foilenLoginWebSecurityConfigurerAdapter() {
        return new FoilenLoginWebSecurityConfigurerAdapterStub();
    }

}
