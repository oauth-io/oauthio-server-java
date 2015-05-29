package io.oauth.server.model;

import io.oauth.server.JsonUtil;


import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
public class AuthCallback  {
  
  private String callback_uri = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("callback_uri")
  public String getCallbackUri() {
    return callback_uri;
  }
  public void setCallbackUri(String callback_uri) {
    this.callback_uri = callback_uri;
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
    sb.append("class AuthCallback {\n");
    
    sb.append("  callback_uri: ").append(callback_uri).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
