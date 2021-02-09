/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api;

import java.util.Collections;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.foilen.login.api.to.FoilenLoginToken;
import com.foilen.login.api.to.FoilenLoginUser;

public class LoginClientImpl implements LoginClient {

    private RestTemplate restTemplate = new RestTemplate();

    private String appId;
    private String loginBaseUrl;

    public LoginClientImpl(String appId, String loginBaseUrl) {
        this.appId = appId;
        this.loginBaseUrl = loginBaseUrl;
    }

    @Override
    public FoilenLoginUser createOrFindByEmail(String email) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginBaseUrl + "/api/createOrFindByEmail/{email}");
        builder.queryParam("appId", appId);
        return restTemplate.getForObject(builder.buildAndExpand(Collections.singletonMap("email", email)).toUri(), FoilenLoginUser.class);
    }

    @Override
    public FoilenLoginToken createToken(String redirectUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginBaseUrl + "/api/createToken");
        builder.queryParam("redirectUrl", redirectUrl);
        FoilenLoginToken createTokenTo = restTemplate.getForObject(builder.build().toUri(), FoilenLoginToken.class);

        // Set the login url
        createTokenTo.setLoginUrl(loginBaseUrl + "/login/" + createTokenTo.getToken());

        return createTokenTo;
    }

    @Override
    public FoilenLoginUser findByEmail(String email) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginBaseUrl + "/api/findByEmail/{email}");
        builder.queryParam("appId", appId);
        return restTemplate.getForObject(builder.buildAndExpand(Collections.singletonMap("email", email)).toUri(), FoilenLoginUser.class);
    }

    @Override
    public FoilenLoginUser findByUserId(String userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginBaseUrl + "/api/findByUserId/{userId}");
        builder.queryParam("appId", appId);
        return restTemplate.getForObject(builder.buildAndExpand(Collections.singletonMap("userId", userId)).toUri(), FoilenLoginUser.class);
    }

    @Override
    public String findUserIdByToken(String token) {
        return restTemplate.getForObject(loginBaseUrl + "/api/findUserIdByToken/{token}", String.class, Collections.singletonMap("token", token));
    }

    public String getAppId() {
        return appId;
    }

    public String getLoginBaseUrl() {
        return loginBaseUrl;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
