/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security.cookie;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.foilen.smalltools.hash.HashSha512;
import com.foilen.smalltools.tools.DateTools;
import com.google.common.base.Strings;

public class FoilenLoginCookieServiceImpl implements FoilenLoginCookieService {

    private String cookieLoginToken = "foilen_login_token";
    private String cookieUserName = "foilen_user_id";
    private String cookieDateName = "foilen_date";
    private String cookieSignatureName = "foilen_signature";
    private String cookieSignatureSalt;
    private int cookieExpirationDays = 1;

    private void addCookies(String userId, HttpServletResponse response, boolean httpsSecuredOnly) {
        if (userId == null) {
            response.addCookie(createCookie(cookieUserName, null, httpsSecuredOnly));
            response.addCookie(createCookie(cookieSignatureName, null, httpsSecuredOnly));
            response.addCookie(createCookie(cookieDateName, null, httpsSecuredOnly));
        } else {
            Date date = new Date();
            response.addCookie(createCookie(cookieUserName, userId, httpsSecuredOnly));
            response.addCookie(createCookie(cookieSignatureName, HashSha512.hashString(userId + date.getTime() + cookieSignatureSalt), httpsSecuredOnly));
            response.addCookie(createCookie(cookieDateName, String.valueOf(date.getTime()), httpsSecuredOnly));
        }
    }

    private Cookie createCookie(String name, String value, boolean httpsSecuredOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setSecure(httpsSecuredOnly);

        if (value == null) {
            cookie.setMaxAge(0);
        }

        return cookie;
    }

    private String findUser(Cookie[] cookies) {

        if (cookies == null) {
            return null;
        }

        String cUserId = null;
        String cSignature = null;
        Date cDate = null;
        for (Cookie cookie : cookies) {
            if (cookieUserName.equals(cookie.getName())) {
                cUserId = cookie.getValue();
            }
            if (cookieSignatureName.equals(cookie.getName())) {
                cSignature = cookie.getValue();
            }
            if (cookieDateName.equals(cookie.getName())) {
                try {
                    cDate = new Date(Long.valueOf(cookie.getValue()));
                } catch (Exception e) {
                }
            }
        }

        // Check if there is one cookie and it is the right signature
        String userId = null;
        if (!Strings.isNullOrEmpty(cUserId) && !Strings.isNullOrEmpty(cSignature) && cDate != null) {
            // Check not expired
            if (!DateTools.isExpired(cDate, Calendar.DAY_OF_MONTH, cookieExpirationDays)) {
                // Check signature
                String correctSignature = HashSha512.hashString(cUserId + cDate.getTime() + cookieSignatureSalt);
                if (correctSignature.equals(cSignature)) {
                    userId = cUserId;
                }
            }
        }

        return userId;
    }

    public String getCookieDateName() {
        return cookieDateName;
    }

    public int getCookieExpirationDays() {
        return cookieExpirationDays;
    }

    public String getCookieLoginToken() {
        return cookieLoginToken;
    }

    public String getCookieSignatureName() {
        return cookieSignatureName;
    }

    public String getCookieSignatureSalt() {
        return cookieSignatureSalt;
    }

    public String getCookieUserName() {
        return cookieUserName;
    }

    @Override
    public String getLoggedInUser(HttpServletRequest request) {
        return findUser(request.getCookies());
    }

    @Override
    public String getLogInToken(HttpServletRequest request) {

        // Get all the cookies
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        // Find the right cookie
        String loginToken = null;
        for (Cookie cookie : cookies) {
            if (cookieLoginToken.equals(cookie.getName())) {
                loginToken = cookie.getValue();
            }
        }
        return loginToken;
    }

    @PostConstruct
    public void init() {
        Assert.notNull(cookieSignatureSalt, "You must set a cookie signature salt");
    }

    @Override
    public void removeLoggedInUser(HttpServletResponse response, boolean httpsSecuredOnly) {
        addCookies(null, response, httpsSecuredOnly);
    }

    public void setCookieDateName(String cookieDateName) {
        this.cookieDateName = cookieDateName;
    }

    public void setCookieExpirationDays(int cookieExpirationDays) {
        this.cookieExpirationDays = cookieExpirationDays;
    }

    public void setCookieLoginToken(String cookieLoginToken) {
        this.cookieLoginToken = cookieLoginToken;
    }

    public void setCookieSignatureName(String cookieSignatureName) {
        this.cookieSignatureName = cookieSignatureName;
    }

    public void setCookieSignatureSalt(String cookieSignatureSalt) {
        this.cookieSignatureSalt = cookieSignatureSalt;
    }

    public void setCookieUserName(String cookieUserName) {
        this.cookieUserName = cookieUserName;
    }

    @Override
    public void setLoggedInUser(String userId, HttpServletResponse response, boolean httpsSecuredOnly) {
        addCookies(userId, response, httpsSecuredOnly);
    }

    @Override
    public void setLogInToken(String loginToken, HttpServletResponse response, boolean httpsSecuredOnly) {
        response.addCookie(createCookie(cookieLoginToken, loginToken, httpsSecuredOnly));
    }

}
