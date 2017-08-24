/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.foilen.login.api.to.FoilenLoginToken;
import com.foilen.login.api.to.FoilenLoginUser;
import com.foilen.smalltools.tools.JsonTools;

public class TestApiApp {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("You need to specify the base url and the app id");
            System.out.println("e.g: https://www.xxxxxx.com xxxxxxxxxxx");
            return;
        }

        File tmpConfigFile = File.createTempFile("login-config", ".json");
        System.out.println("Saving the config file in: " + tmpConfigFile.getAbsolutePath());
        LoginConfigDetails configDetails = new LoginConfigDetails();
        configDetails.setAppId(args[1]);
        configDetails.setBaseUrl(args[0]);
        JsonTools.writeToFile(tmpConfigFile, configDetails);

        System.setProperty("login.configFile", tmpConfigFile.getAbsolutePath());

        @SuppressWarnings("resource")
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(LoginClientSpringConfig.class);
        LoginClient loginClient = applicationContext.getBean(LoginClient.class);

        // Find by email
        System.out.println("Find by email");
        FoilenLoginUser foilenLoginUser = loginClient.findByEmail("admin@foilen.com");
        System.out.println("-> " + foilenLoginUser);

        // Create or find by email
        System.out.println("Create or find by email");
        foilenLoginUser = loginClient.createOrFindByEmail("admin@foilen.com");
        System.out.println("-> " + foilenLoginUser);
        foilenLoginUser = loginClient.createOrFindByEmail(Math.random() + "@foilen.com");
        System.out.println("-> " + foilenLoginUser);

        // Create the token
        System.out.println("Creating login token that redirects to Google");
        FoilenLoginToken createToken = loginClient.createToken("http://www.google.com");
        System.out.println("-> " + createToken);

        // Wait for the user to log in
        System.out.println("Waiting for the user to log in");
        String userId = null;
        while (true) {
            Thread.sleep(5000);

            userId = loginClient.findUserIdByToken(createToken.getToken());
            System.out.println("-> " + userId);

            if (userId != null) {
                break;
            }
        }

        // Get user details
        System.out.println("Getting user details");
        foilenLoginUser = loginClient.findByUserId(userId);
        System.out.println("-> " + foilenLoginUser);

    }

}
