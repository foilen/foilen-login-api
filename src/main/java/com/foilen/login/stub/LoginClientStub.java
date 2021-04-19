/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub;

import java.util.HashMap;
import java.util.Map;

import com.foilen.login.api.LoginClient;
import com.foilen.login.api.to.FoilenLoginToken;
import com.foilen.login.api.to.FoilenLoginUser;
import com.foilen.smalltools.hash.HashSha1;

public class LoginClientStub implements LoginClient {

    private Map<String, FoilenLoginUser> loginUserById = new HashMap<>();

    @Override
    public FoilenLoginUser createOrFindByEmail(String email) {
        String emailLower = email.toLowerCase();
        String id = HashSha1.hashString(emailLower);
        return loginUserById.computeIfAbsent(id, (d) -> new FoilenLoginUser().setUserId(id).setEmail(emailLower));
    }

    @Override
    public FoilenLoginToken createToken(String redirectUrl) {
        throw new RuntimeException("Stub not implemented");
    }

    @Override
    public FoilenLoginUser findByEmail(String email) {
        String emailLower = email.toLowerCase();
        String id = HashSha1.hashString(emailLower);
        return loginUserById.get(id);
    }

    @Override
    public FoilenLoginUser findByUserId(String userId) {
        return loginUserById.get(userId);
    }

    @Override
    public String findUserIdByToken(String token) {
        throw new RuntimeException("Stub not implemented");
    }

}
