/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2020 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginConfigDetails {

    private String appId;
    private String baseUrl;

    // Optional if have a trusted certificate
    private String certFile;
    private String certText;

    public String getAppId() {
        return appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCertFile() {
        return certFile;
    }

    public String getCertText() {
        return certText;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public void setCertText(String certText) {
        this.certText = certText;
    }

}
