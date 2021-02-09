/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017-2021 Foilen (https://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.stub.spring.services;

import com.foilen.login.api.to.FoilenLoginUser;
import com.foilen.login.spring.client.security.FoilenLoginUserDetails;
import com.foilen.login.spring.services.FoilenLoginService;
import com.foilen.smalltools.tools.JsonTools;

public class FoilenLoginServiceStub implements FoilenLoginService {

    @Override
    public FoilenLoginUserDetails getLoggedInUserDetails() {
        return new FoilenLoginUserDetails(JsonTools.readFromResource("FoilenLoginUser-admin.json", FoilenLoginUser.class, this.getClass()));
    }

}
