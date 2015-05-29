package io.oauth.server.model;

import io.oauth.server.JsonUtil;


import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
public class Client  {
  
  private String name = null;
  private String description = null;
  private String client_id = null;
  private String client_secret = null;
  private String provider_id = null;
  private String redirect_uri = null;
  private String user_id = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
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
  @JsonProperty("client_secret")
  public String getClientSecret() {
    return client_secret;
  }
  public void setClientSecret(String client_secret) {
    this.client_secret = client_secret;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("provider_id")
  public String getProviderId() {
    return provider_id;
  }
  public void setProviderId(String provider_id) {
    this.provider_id = provider_id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("redirect_uri")
  public String getRedirectUri() {
    return redirect_uri;
  }
  public void setRedirectUri(String redirect_uri) {
    this.redirect_uri = redirect_uri;
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
    sb.append("class Client {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  client_id: ").append(client_id).append("\n");
    sb.append("  client_secret: ").append(client_secret).append("\n");
    sb.append("  provider_id: ").append(provider_id).append("\n");
    sb.append("  redirect_uri: ").append(redirect_uri).append("\n");
    sb.append("  user_id: ").append(user_id).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
