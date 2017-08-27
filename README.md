# About

This is the login service API.

# Usage

## Dependency

You can see the latest version and the Maven and Gradle settings here:

https://bintray.com/foilen/maven/com.foilen:foilen-login-api

## HTTPS Client

You need to create a json client configuration file that maps the object LoginConfigDetails.

A minimal file contains:

```
{
	"baseUrl" : "https://login.example.com",
	"appId" : "xxxxxxx"
}
```

If you are using a certificate that is not in your Java trusts store, you can specify it in a file and give the path in *certFile* or put the certificate directly in *certText*. 

Once you created the file, you need to define the property "login.configFile" with the path to the file.

Use the Spring Config class: *com.foilen.login.api.LoginClientSpringConfig*. That will give you a *LoginClient* bean.

## Spring Security

### Real

You need to define the property "login.cookieSignatureSalt" with a salt that you share between all your apps that will use the same cookie.

Use the Spring Config class: *com.foilen.login.spring.client.security.FoilenLoginSecurityConfig*.

### Stub

Use the Spring Config class: *com.foilen.login.stub.spring.client.security.FoilenLoginSecurityStubConfig*.

The logged-in user will be:

```
{
	"userId" : "111111",
	"firstName" : "The",
	"lastName" : "Admin",
	"email" : "admin@example.com",
	"address" : "",
	"lang" : "en"
}
```

### Common

Per default, all the pages will need users to authenticate themselves. If you want some pages to be accessible without being logged in, set the property "login.exclude". It is a comma separated list of paths (AntPathRequestMatcher) to excludes. E.g:

```
System.setProperty("login.exclude", "/css/**,/images/**");
```
