package io.oauth.server.api;

import io.oauth.server.ApiException;
import io.oauth.server.ApiInvoker;

import io.oauth.server.model.*;

import java.util.*;

import io.oauth.server.model.AuthCallback;
import io.oauth.server.model.TokenInfos;
import io.oauth.server.model.TokenSet;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class AuthorizationApi {
  String basePath = "https://oauth.io/oauth2";
  ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }

  public void setProviderKeys(String providerId, String providerSecret) {
    apiInvoker.setProviderKeys(providerId,providerSecret);
  }

  
  /**
   * Authorize a user
   * This endpoint is called when the user allow or deny permission \nin the decision page\n
   * @param clientId Client ID
   * @param decision Must be set to &#39;1&#39; if the user allowed the app, or &#39;0&#39; if the user denied the authorization
   * @param userId User ID
   * @param scope List of permission (space delimiter)
   * @param redirectUri Redirect URI
   * @param state CORS token
   * @param responseType Response type
   * @return AuthCallback
   */
  public AuthCallback authorize (String clientId, String decision, String userId, String scope, String redirectUri, String state, String responseType) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/authorization";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    if (clientId != null)
      queryParams.put("client_id", ApiInvoker.parameterToString(clientId));
    if (userId != null)
      queryParams.put("user_id", ApiInvoker.parameterToString(userId));
    if (scope != null)
      queryParams.put("scope", ApiInvoker.parameterToString(scope));
    if (redirectUri != null)
      queryParams.put("redirect_uri", ApiInvoker.parameterToString(redirectUri));
    if (state != null)
      queryParams.put("state", ApiInvoker.parameterToString(state));
    if (responseType != null)
      queryParams.put("response_type", ApiInvoker.parameterToString(responseType));
    
    
    formParams.put("decision", ApiInvoker.parameterToString(decision));
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (AuthCallback) ApiInvoker.deserialize(response, "", AuthCallback.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Check the validity of an access token and give back his permission, \nclient_id and user_id\n
   * 
   * @param accessToken The access token you want to check
   * @return TokenInfos
   */
  public TokenInfos check (String accessToken) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/check";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    if (accessToken != null)
      queryParams.put("access_token", ApiInvoker.parameterToString(accessToken));
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (TokenInfos) ApiInvoker.deserialize(response, "", TokenInfos.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Access token retrieval (with code) or refresh (with refresh token)
   * 
   * @param clientId Client ID
   * @param clientSecret Client Secret
   * @param grantType authorization_code or client_credential or refresh_token
   * @param scope Space separated scope list (e.g. \&quot;email friends_list\&quot;). Used only if grant_type = client_credential
   * @param code Code received from /authorize step. Required if grant_type = authorization_code
   * @param refreshToken Refresh token (long-live token) used to refresh the access token (short-live token). Required if grant_type = refresh_token
   * @return TokenSet
   */
  public TokenSet token (String clientId, String clientSecret, String grantType, String scope, String code, String refreshToken) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/token";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    formParams.put("client_id", ApiInvoker.parameterToString(clientId));
    formParams.put("client_secret", ApiInvoker.parameterToString(clientSecret));
    formParams.put("grant_type", ApiInvoker.parameterToString(grantType));
    formParams.put("scope", ApiInvoker.parameterToString(scope));
    formParams.put("code", ApiInvoker.parameterToString(code));
    formParams.put("refresh_token", ApiInvoker.parameterToString(refreshToken));
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (TokenSet) ApiInvoker.deserialize(response, "", TokenSet.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
