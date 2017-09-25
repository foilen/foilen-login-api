/*
    Foilen Login API
    https://github.com/foilen/foilen-login-api
    Copyright (c) 2017 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.login.api;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.foilen.smalltools.crypt.spongycastle.cert.RSACertificate;
import com.foilen.smalltools.tools.JsonTools;
import com.google.common.base.Strings;

/**
 * Use this config to use the login system API.
 */
@Configuration
public class LoginClientSpringConfig {

    @Autowired
    public Environment environment;

    @Bean
    public LoginClient loginClient() {
        try {

            String loginConfigFile = environment.getRequiredProperty("login.configFile");
            LoginConfigDetails config = JsonTools.readFromFile(loginConfigFile, LoginConfigDetails.class);

            String appId = config.getAppId();
            String baseUrl = config.getBaseUrl();

            LoginClientImpl loginClient = new LoginClientImpl(appId, baseUrl);

            // Trust the certificate itself
            String certFile = config.getCertFile();
            RSACertificate rsaCertificate = null;
            if (Strings.isNullOrEmpty(certFile)) {
                String certText = config.getCertText();
                if (!Strings.isNullOrEmpty(certText)) {
                    // From text
                    rsaCertificate = RSACertificate.loadPemFromString(certText);
                }
            } else {
                // From file
                rsaCertificate = RSACertificate.loadPemFromFile(certFile);
            }

            // Configure SSL with a custom cert if needed
            if (rsaCertificate != null) {
                KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
                truststore.load(null, null);
                truststore.setCertificateEntry("foilen-login", rsaCertificate.getCertificate());
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(truststore, null).build();

                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
                HttpClient httpClient = HttpClientBuilder.create().useSystemProperties().setSSLSocketFactory(sslConnectionSocketFactory).build();
                ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
                RestTemplate restTemplate = new RestTemplate(requestFactory);
                loginClient.setRestTemplate(restTemplate);
            }

            return loginClient;
        } catch (Exception e) {
            throw new LoginException(e);
        }

    }
}
