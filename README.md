OAuth.io Server API - Java SDK
===================

### Introduction

The OAuth 2.0 framework allows you to secure your API endpoints with an authentication layer, according the the [RFC 6749](https://tools.ietf.org/html/rfc6749). This lets apps call the endpoints on behalf of your users, after having asked for a scope of permissions.

The authentication is done thanks to an access token, which is given with any call to the API.

The token is recognized, and the associated user, associated app and the available permissions scope are found so the API is able to decide if it will send resource or not.

To obtain the access token, the following processs is used:

- First, the app (the client) registers on the API provider's developer portal, to obtain a client id and a client secret.
- Then, a user (already subscribed to the API provider's service), launches an action on the client's website, which needs resources from the provider's API.
- The user is redirected on the provider's website (usually on the `/authorize` endpoint) with information about who the client is
- The user logs in to the provider's website, and responds to a **decision form**, in which he can see what permissions the client app wants on his account on the provider's API
- Upon acceptation, the user is redirected to the client's website, on a specific callback URL, with an authentication code
- The client then exchanges the authentication code for an access token by calling the `/token` endpoint on the provider's API.

Implementing all this, be it from the client's point of view, or from the provider's, is quite long and tedious.

> For the client part, check out [OAuth.io](https://oauth.io) and [oauthd](https://github.com/oauth-io/oauthd)'s client services, which will enable you to integrate over 100 APIs in your application in a matter of minutes.


Pre-requisite
-------------

**You need the following installed and available in your $PATH:**

* [Java 7 or greater](http://java.oracle.com)

* [Apache maven 3.0.3 or greater](http://maven.apache.org/)

**Create a provider on OAuth.io/oauthd**

First, you need to create a provider on [OAuth.io](https://oauth.io), or in your [oauthd instance](https://github.com/oauth-io/oauthd).

There you will be able to configure the provider with a **name**, and a complete **authorization items** list (with which clients will define their scope).

You'll be able to retrieve the **provider_id** and the **provider_secret**, which will enable you to initialize the SDK on your Java API server.

**User management**

Your server must have some kind of user management, as client applications will use your API on behalf of your users.

Installation
------------

To install the SDK, you need to add this repository into your `pom.xml`:

```xml
  <repositories>
    <repository>
        <id>oauthio-server-java-mvn-repo</id>
        <url>https://raw.github.com/oauth-io/oauthio-server-java/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
  </repositories>
```

and add it as a dependency:

```xml
  <dependencies>
    <dependency>
      <groupId>io.oauth</groupId>
      <artifactId>oauthio-java-client</artifactId>
      <version>1.0.0</version>
    </dependency>
  </dependencies>
```

OAuth 2.0 endpoints
-------------------------

### Step 1 - Create the GET /authorize endpoint

First, you need to create the `GET /authorize` endpoint in your backend server, which will redirect the user to a login page if he is not logged in, and then, show the decision form. 

If the user is not logged in, you need to redirect him to the log in page **with the same GET parameters**, and the location of the authorization endpoint. Once the user is logged in, he should be redirected once more to the authorization endpoint with the original get parameters.

This decision page endpoint should reply with *text/html* content-type and should contain at least:

- The client informations: **name**, **description**. These informations can be received through the [ClientApi.getClient]() method.

- The scopes of the authorization asked by the client. If present, this **must** be a list of space-delimited, case-sensitive strings passed in the url query.

- An accept and deny button that calls the `POST /authorize` endpoint on your backend.

## Step 2 - Create the POST /authorize endpoint

Once the user allow or deny the decision page, it should send a POST /authorize request to your server, so you should define this endpoint in your backend.

In this endpoint, you have to call the [AuthorizationApi.authorize]() method with the given query and body parameters. That will reply an [AuthCallback]() object that contains the callback url where you must redirect the user, with a `code` in the query parameters.

## Step 3 - Create the POST /token endpoint

Once the user has its code, he will make a call to your server to exchange this code for a valid `access_token`/`refresh_token`.

In this endpoint, you must call the [AuthorizationApi.token]() method with the given query and body parameters. This methods replies an [TokenSet]() object that you must send back to your user. We recommend to send it back with the *application/json* type using the [TokenSet.toJson]() method.

Here, the user has a valid access token which can be used on your API to access restricted content/feature of your API.

## Step 4 - Access Token and Scopes validity

Now, you need to secure the API endpoint you need to restrict the access to authorized users. To do this, you just need to call the [AuthorizationApi.check]() method with the  **access_token** received either from the Authorization http header (bearer token) or body/query parameter. This API call return all information about the access token (the user_id, the scopes he has authorized etc..) in a [TokenInfos]() object. You can use the scope to restrict some feature depending the permissions the user has accepted.

## Step 5 - Link OAuth.io with your server

Now that you have a working OAuth server, you need some clients to use your API.

You have 2 ways to create clients on your providers:

- Make your own developer portal on top of OAuth.io Server API
- Use OAuth.io as your developer portal. 

The easiest way is by directly using OAuth.io as your developer portal as you have nothing to code. You just need to link your URL in your OAuth.io dashboard. Then, other OAuth.io users will be able to add your provider in one click. In the OAuth.io dashboard, you can of course access all information about the developer who created an app with your provider (email, app name, app description, analytics).

## Step 6 - Make your own developer portal

At this step, you have a working OAuth and developer can already use you via OAuth.io. You can also build your own developer portal using the OAuth.io Server API.

The [ClientApi]() object contains all the useful methods to create, edit or remove a client, get a client informations or a list of clients filtered by user, or regenerate the secret key of a client.
