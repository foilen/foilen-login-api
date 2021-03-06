/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api.to;

public class FoilenLoginUser {

    private String userId;

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    private String lang = "en";
    private boolean isAdmin;

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLang() {
        return lang;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public FoilenLoginUser setAddress(String address) {
        this.address = address;
        return this;
    }

    public FoilenLoginUser setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public FoilenLoginUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public FoilenLoginUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public FoilenLoginUser setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public FoilenLoginUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public FoilenLoginUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FoilenLoginUser [userId=");
        builder.append(userId);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
        builder.append(", address=");
        builder.append(address);
        builder.append(", lang=");
        builder.append(lang);
        builder.append(", isAdmin=");
        builder.append(isAdmin);
        builder.append("]");
        return builder.toString();
    }

}
