/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.spring.client.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.foilen.login.api.to.FoilenLoginUser;

public class FoilenLoginUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userId;
    private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    private String lang = "en";

    public FoilenLoginUserDetails(FoilenLoginUser foilenLoginUser) {
        userId = foilenLoginUser.getUserId();
        firstName = foilenLoginUser.getFirstName();
        lastName = foilenLoginUser.getLastName();
        email = foilenLoginUser.getEmail();
        address = foilenLoginUser.getAddress();
        lang = foilenLoginUser.getLang();
    }

    public FoilenLoginUserDetails(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public FoilenLoginUserDetails(String userId, String email, Set<GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FoilenLoginUserDetails [userId=");
        builder.append(userId);
        builder.append(", authorities=");
        builder.append(authorities);
        builder.append(", email=");
        builder.append(email);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", address=");
        builder.append(address);
        builder.append(", lang=");
        builder.append(lang);
        builder.append("]");
        return builder.toString();
    }

}
