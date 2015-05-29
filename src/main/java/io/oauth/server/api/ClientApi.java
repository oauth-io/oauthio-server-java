package io.oauth.server.api;

import io.oauth.server.ApiException;
import io.oauth.server.ApiInvoker;

import io.oauth.server.model.*;

import java.util.*;

import io.oauth.server.model.Client;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class ClientApi {
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
   * Update a client
   * 
   * @param id Client id of the app to update
   * @param name Name of the client&#39;s app
   * @param description Description of the client&#39;s app
   * @param redirectUri Whitelisted redirect uri
   * @param userId User id of the client&#39;s app
   * @return Client
   */
  public Client updateClient (String id, String name, String description, String redirectUri, String userId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    formParams.put("id", ApiInvoker.parameterToString(id));
    formParams.put("name", ApiInvoker.parameterToString(name));
    formParams.put("description", ApiInvoker.parameterToString(description));
    formParams.put("redirect_uri", ApiInvoker.parameterToString(redirectUri));
    formParams.put("user_id", ApiInvoker.parameterToString(userId));
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (Client) ApiInvoker.deserialize(response, "", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Create a client
   * 
   * @param name Name of the client&#39;s app
   * @param description Description of the client&#39;s app
   * @param redirectUri Whitelisted redirect uri
   * @param userId User id of the client&#39;s app
   * @return Client
   */
  public Client createClient (String name, String description, String redirectUri, String userId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    formParams.put("name", ApiInvoker.parameterToString(name));
    formParams.put("description", ApiInvoker.parameterToString(description));
    formParams.put("redirect_uri", ApiInvoker.parameterToString(redirectUri));
    formParams.put("user_id", ApiInvoker.parameterToString(userId));
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (Client) ApiInvoker.deserialize(response, "", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Retrieves all clients
   * 
   * @return List<Client>
   */
  public List<Client> getAllClients () throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients/all";

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (List<Client>) ApiInvoker.deserialize(response, "array", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Retrieves all clients filtered by a given user_id
   * 
   * @param userId User id
   * @return List<Client>
   */
  public List<Client> getAllClientsByUser (String userId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients/all/{user_id}"
      .replaceAll("\\{" + "user_id" + "\\}", apiInvoker.escapeString(userId.toString()));

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (List<Client>) ApiInvoker.deserialize(response, "array", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Regenerate the API key&#39;s client
   * 
   * @param clientId Client id
   * @return Client
   */
  public Client resetClientKeys (String clientId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients/keygen/{client_id}"
      .replaceAll("\\{" + "client_id" + "\\}", apiInvoker.escapeString(clientId.toString()));

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (Client) ApiInvoker.deserialize(response, "", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Retrieves a client by its client_id
   * 
   * @param clientId Client id of the client to receive
   * @return Client
   */
  public Client getClient (String clientId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients/{client_id}"
      .replaceAll("\\{" + "client_id" + "\\}", apiInvoker.escapeString(clientId.toString()));

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return (Client) ApiInvoker.deserialize(response, "", Client.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Remove a client by its client_id
   * 
   * @param clientId Client id of the client to receive
   * @return void
   */
  public void deleteClient (String clientId) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/clients/{client_id}"
      .replaceAll("\\{" + "client_id" + "\\}", apiInvoker.escapeString(clientId.toString()));

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, String> formParams = new HashMap<String, String>();
    
    
    
    try {
      String response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams, postBody, headerParams, formParams, "application/json");
      if(response != null){
        return ;
      }
      else {
        return ;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
