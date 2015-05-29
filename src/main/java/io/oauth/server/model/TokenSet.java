package io.oauth.server.model;

import io.oauth.server.JsonUtil;


import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
public class TokenSet  {
  
  private String refresh_token = null;
  private String token_type = null;
  private String access_token = null;
  private Integer expires_in = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("refresh_token")
  public String getRefreshToken() {
    return refresh_token;
  }
  public void setRefreshToken(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("token_type")
  public String getTokenType() {
    return token_type;
  }
  public void setTokenType(String token_type) {
    this.token_type = token_type;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("access_token")
  public String getAccessToken() {
    return access_token;
  }
  public void setAccessToken(String access_token) {
    this.access_token = access_token;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("expires_in")
  public Integer getExpiresIn() {
    return expires_in;
  }
  public void setExpiresIn(Integer expires_in) {
    this.expires_in = expires_in;
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
    sb.append("class TokenSet {\n");
    
    sb.append("  refresh_token: ").append(refresh_token).append("\n");
    sb.append("  token_type: ").append(token_type).append("\n");
    sb.append("  access_token: ").append(access_token).append("\n");
    sb.append("  expires_in: ").append(expires_in).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
