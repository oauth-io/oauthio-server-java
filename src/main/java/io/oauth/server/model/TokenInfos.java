package io.oauth.server.model;

import io.oauth.server.JsonUtil;

import java.util.*;
import io.oauth.server.model.Client;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
public class TokenInfos  {
  
  private String ttl = null;
  private String token = null;
  private List<String> scope = new ArrayList<String>() ;
  private Client client = null;
  private String client_id = null;
  private String user_id = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("ttl")
  public String getTtl() {
    return ttl;
  }
  public void setTtl(String ttl) {
    this.ttl = ttl;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("token")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("scope")
  public List<String> getScope() {
    return scope;
  }
  public void setScope(List<String> scope) {
    this.scope = scope;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("client")
  public Client getClient() {
    return client;
  }
  public void setClient(Client client) {
    this.client = client;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("client_id")
  public String getClientId() {
    return client_id;
  }
  public void setClientId(String client_id) {
    this.client_id = client_id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("user_id")
  public String getUserId() {
    return user_id;
  }
  public void setUserId(String user_id) {
    this.user_id = user_id;
  }

  

  public String toJson() {
    try {
      return JsonUtil.getJsonMapper().writeValueAsString(this);
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenInfos {\n");
    
    sb.append("  ttl: ").append(ttl).append("\n");
    sb.append("  token: ").append(token).append("\n");
    sb.append("  scope: ").append(scope).append("\n");
    sb.append("  client: ").append(client).append("\n");
    sb.append("  client_id: ").append(client_id).append("\n");
    sb.append("  user_id: ").append(user_id).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
