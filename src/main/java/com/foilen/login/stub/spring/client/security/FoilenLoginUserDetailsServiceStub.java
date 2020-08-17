/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring.client.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.foilen.login.api.to.FoilenLoginUser;
import com.foilen.login.spring.client.security.FoilenLoginUserDetails;
import com.foilen.login.stub.spring.services.FoilenLoginServiceStub;
import com.foilen.smalltools.tools.JsonTools;

public class FoilenLoginUserDetailsServiceStub implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new FoilenLoginUserDetails(JsonTools.readFromResource("FoilenLoginUser-admin.json", FoilenLoginUser.class, FoilenLoginServiceStub.class));
    }

}
