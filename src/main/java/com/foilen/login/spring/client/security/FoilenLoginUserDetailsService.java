/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.foilen.login.api.LoginClient;
import com.foilen.login.api.to.FoilenLoginUser;

public class FoilenLoginUserDetailsService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(FoilenLoginUserDetailsService.class);

    @Autowired
    private LoginClient loginClient;

    @Autowired
    private UserCache userCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userCache.getUserFromCache(username);
        if (userDetails == null) {
            FoilenLoginUserDetails foilenLoginUserDetails = new FoilenLoginUserDetails(username, null);

            // Get the details remotely
            try {
                FoilenLoginUser foilenLoginUser = loginClient.findByUserId(username);
                if (foilenLoginUser.getUserId() != null) {
                    foilenLoginUserDetails = new FoilenLoginUserDetails(foilenLoginUser);
                }
            } catch (Exception e) {
                logger.error("Problem retrieving details", e);
            }

            userCache.putUserInCache(foilenLoginUserDetails);
            userDetails = foilenLoginUserDetails;
        }

        return userDetails;
    }

}
