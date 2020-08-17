/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.foilen.login.spring.client.security.FoilenLoginUserDetails;

public class FoilenLoginServiceImpl implements FoilenLoginService {

    @Override
    public FoilenLoginUserDetails getLoggedInUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                return (FoilenLoginUserDetails) authentication.getPrincipal();
            }
        }
        return null;
    }

}
