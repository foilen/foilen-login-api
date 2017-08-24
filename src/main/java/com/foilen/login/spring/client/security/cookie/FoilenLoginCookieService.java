/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017 Foilen (http://foilen.com)

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

    void removeLoggedInUser(HttpServletResponse response, boolean isHttpOnly);

    void setLoggedInUser(String userId, HttpServletResponse response, boolean isHttpOnly);

    void setLogInToken(String loginToken, HttpServletResponse response, boolean isHttpOnly);
}
