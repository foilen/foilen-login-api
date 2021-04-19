/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.foilen.login.api.LoginClient;
import com.foilen.login.stub.LoginClientStub;

@Configuration
public class LoginClientSpringStubConfig {

    @Bean
    public LoginClient loginClient() {
        return new LoginClientStub();
    }

}
