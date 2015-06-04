package io.oauth.server;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.multipart.FormDataMultiPart;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;

import java.net.URLEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ApiInvoker {
  private static ApiInvoker INSTANCE = new ApiInvoker();
  private Map<String, Client> hostMap = new HashMap<String, Client>();
  private Map<String, String> defaultHeaderMap = new HashMap<String, String>();
  private boolean isDebug = false;

  /**
   * ISO 8601 date time format.
   * @see https://en.wikipedia.org/wiki/ISO_8601
   */
  public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  /**
   * ISO 8601 date format.
   * @see https://en.wikipedia.org/wiki/ISO_8601
   */
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  static {
    // Use UTC as the default time zone.
    DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));

    // Set default User-Agent.
    setUserAgent("Java-Swagger");
  }

  public static void setUserAgent(String userAgent) {
    INSTANCE.addDefaultHeader("User-Agent", userAgent);
  }

  public static Date parseDateTime(String str) {
    try {
      return DATE_TIME_FORMAT.parse(str);
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static Date parseDate(String str) {
    try {
      return DATE_FORMAT.parse(str);
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static String formatDateTime(Date datetime) {
    return DATE_TIME_FORMAT.format(datetime);
  }

  public static String formatDate(Date date) {
    return DATE_FORMAT.format(date);
  }

  public static String parameterToString(Object param) {
    if (param == null) {
      return "";
    } else if (param instanceof Date) {
      return formatDateTime((Date) param);
    } else if (param instanceof Collection) {
      StringBuilder b = new StringBuilder();
      for(Object o : (Collection)param) {
        if(b.length() > 0) {
          b.append(",");
        }
        b.append(String.valueOf(o));
      }
      return b.toString();
    } else {
      return String.valueOf(param);
    }
  }
  public void enableDebug() {
    isDebug = true;
  }

  public static ApiInvoker getInstance() {
    return INSTANCE;
  }

  public void addDefaultHeader(String key, String value) {
     defaultHeaderMap.put(key, value);
  }

  public void initialize(String providerId, String providerSecret) {
    String basicAuth = providerId + ":" + providerSecret;
    String authorization = new String(Base64Coder.encode(basicAuth.getBytes()));
    defaultHeaderMap.put("Authorizationp", "Basic " + authorization);
  }

  public String escapeString(String str) {
    try{
      return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
    }
    catch(UnsupportedEncodingException e) {
      return str;
    }
  }

  public static Object deserialize(String json, String containerType, Class cls) throws ApiException {
    if(null != containerType) {
        containerType = containerType.toLowerCase();
    }
    try{
      Map<String, Object> jsonPayload = JsonUtil.getJsonMapper().readValue(json, Map.class);
      Object payloadData = jsonPayload.get("data");
      if(payloadData == null)
        payloadData = jsonPayload;
      if("list".equals(containerType) || "array".equals(containerType)) {
        JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructCollectionType(List.class, cls);
        List response = (List<?>) JsonUtil.getJsonMapper().convertValue(payloadData, typeInfo);
        return response;
      }
      else
        return JsonUtil.getJsonMapper().convertValue(payloadData, cls);
    }
    catch (IOException e) {
      throw new ApiException(500, e.getMessage());
    }
  }

  public static String deserializeError(String json) throws ApiException {
    try{
      Map<String, Object> jsonPayload = JsonUtil.getJsonMapper().readValue(json, Map.class);
      Object payloadData = jsonPayload.get("message");
      if (payloadData == null)
        payloadData = jsonPayload.get("data");
      return JsonUtil.getJsonMapper().convertValue(payloadData, String.class);
    }
    catch (IOException e) {
      return e.getMessage();
    }
  }

  public static String serialize(Object obj) throws ApiException {
    try {
      if (obj != null)
        return JsonUtil.getJsonMapper().writeValueAsString(obj);
      else
        return null;
    }
    catch (Exception e) {
      throw new ApiException(500, e.getMessage());
    }
  }

  public String invokeAPI(String host, String path, String method, Map<String, String> queryParams, Object body, Map<String, String> headerParams, Map<String, String> formParams, String contentType) throws ApiException {
    Client client = getClient(host);

    StringBuilder b = new StringBuilder();

    if (body == null && ! formParams.isEmpty())
      body = formParams;

    for(String key : queryParams.keySet()) {
      String value = queryParams.get(key);
      if (value != null){
        if(b.toString().length() == 0)
          b.append("?");
        else
          b.append("&");
        b.append(escapeString(key)).append("=").append(escapeString(value));
      }
    }
    String querystring = b.toString();

    Builder builder = client.resource(host + path + querystring).accept("application/json");
    for(String key : headerParams.keySet()) {
      builder = builder.header(key, headerParams.get(key));
    }

    for(String key : defaultHeaderMap.keySet()) {
      if(!headerParams.containsKey(key)) {
        builder = builder.header(key, defaultHeaderMap.get(key));
      }
    }
    ClientResponse response = null;

    if("GET".equals(method)) {
      response = (ClientResponse) builder.get(ClientResponse.class);
    }
    else if ("POST".equals(method)) {
      if(body == null)
        response = builder.post(ClientResponse.class, null);
      else
        response = builder.type(contentType).post(ClientResponse.class, serialize(body));
    }
    else if ("PUT".equals(method)) {
      if(body == null)
        response = builder.put(ClientResponse.class, serialize(body));
      else {
        if("application/x-www-form-urlencoded".equals(contentType)) {
          StringBuilder formParamBuilder = new StringBuilder();

          // encode the form params
          for(String key : formParams.keySet()) {
            String value = formParams.get(key);
            if(value != null && !"".equals(value.trim())) {
              if(formParamBuilder.length() > 0) {
                formParamBuilder.append("&");
              }
              try {
                formParamBuilder.append(URLEncoder.encode(key, "utf8")).append("=").append(URLEncoder.encode(value, "utf8"));
              }
              catch (Exception e) {
                // move on to next
              }
            }
          }
          response = builder.type(contentType).put(ClientResponse.class, formParamBuilder.toString());
        }
        else
          response = builder.type(contentType).put(ClientResponse.class, serialize(body));
      }
    }
    else if ("DELETE".equals(method)) {
      if(body == null)
        response = builder.delete(ClientResponse.class);
      else
        response = builder.type(contentType).delete(ClientResponse.class, serialize(body));
    }
    else {
      throw new ApiException(500, "unknown method type " + method);
    }
    if(response.getClientResponseStatus() == ClientResponse.Status.NO_CONTENT) {
      return null;
    }
    else if(response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
      if(response.hasEntity()) {
        return (String) response.getEntity(String.class);
      }
      else {
        return "";
      }
    }
    else if(response.getClientResponseStatus().getStatusCode() == 302) {
      MultivaluedMap<String, String> headers = response.getHeaders();
      String url = headers.getFirst("Location");
      return "{\"data\":{\"callback_uri\":\""+url+"\"}}";
    }
    else {
      String message = "error";
      if(response.hasEntity()) {
        try{
          message = String.valueOf(response.getEntity(String.class));
          message = deserializeError(message);
        }
        catch (RuntimeException e) {
          // e.printStackTrace();
        }
      }
      throw new ApiException(
                response.getClientResponseStatus().getStatusCode(),
                message);
    }
  }

  private Client getClient(String host) {
    if(!hostMap.containsKey(host)) {
      Client client = Client.create();
      if(isDebug)
        client.addFilter(new LoggingFilter());
      hostMap.put(host, client);
    }
    return hostMap.get(host);
  }
}
