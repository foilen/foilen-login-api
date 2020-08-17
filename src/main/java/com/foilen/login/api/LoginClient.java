/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api;

import com.foilen.login.api.to.FoilenLoginToken;
import com.foilen.login.api.to.FoilenLoginUser;

/**
 * To communicate with the login system.
 */
public interface LoginClient {

    /**
     * Get all the details about a user or create it if does not exists.
     *
     * @param email
     *            the email of the user
     * @return the user details
     */
    FoilenLoginUser createOrFindByEmail(String email);

    /**
     * Create a login token.
     *
     * @param redirectUrl
     *            the url that the user will be redirected to after logging in
     * @return the details of the token
     */
    FoilenLoginToken createToken(String redirectUrl);

    /**
     * Get all the details about a user.
     *
     * @param email
     *            the email of the user
     * @return the user details
     */
    FoilenLoginUser findByEmail(String email);

    /**
     * Get all the details about a user.
     *
     * @param userId
     *            the unique user id
     * @return the user details
     */
    FoilenLoginUser findByUserId(String userId);

    /**
     * Get the user that is logged with that token.
     *
     * @param token
     *            the token (must not be expired)
     * @return the user id
     */
    String findUserIdByToken(String token);

}
