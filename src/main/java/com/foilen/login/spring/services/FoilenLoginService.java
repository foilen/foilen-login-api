/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.services;

import com.foilen.login.spring.client.security.FoilenLoginUserDetails;

public interface FoilenLoginService {

    FoilenLoginUserDetails getLoggedInUserDetails();

}
