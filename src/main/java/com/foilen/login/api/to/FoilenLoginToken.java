/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api.to;

import java.util.Date;

public class FoilenLoginToken {

    private String token;
    private Date expiration;
    private String loginUrl;

    public Date getExpiration() {
        return expiration;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getToken() {
        return token;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FoilenLoginToken [");
        if (token != null) {
            builder.append("token=");
            builder.append(token);
            builder.append(", ");
        }
        if (expiration != null) {
            builder.append("expiration=");
            builder.append(expiration);
            builder.append(", ");
        }
        if (loginUrl != null) {
            builder.append("loginUrl=");
            builder.append(loginUrl);
        }
        builder.append("]");
        return builder.toString();
    }

}
