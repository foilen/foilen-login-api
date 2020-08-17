/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages the login cookie and signature.
 */
public interface FoilenLoginCookieService {

    String getLoggedInUser(HttpServletRequest request);

    String getLogInToken(HttpServletRequest request);

    void removeLoggedInUser(HttpServletResponse response, boolean httpsSecuredOnly);

    void setLoggedInUser(String userId, HttpServletResponse response, boolean httpsSecuredOnly);

    void setLogInToken(String loginToken, HttpServletResponse response, boolean httpsSecuredOnly);
}
