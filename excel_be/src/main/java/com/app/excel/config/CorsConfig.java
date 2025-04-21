package com.app.excel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Configuration
public class CorsConfig {

  
 public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
 public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
 public static final String METHODS_NAME = "Access-Control-Allow-Methods";
 public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
 public static final String MAX_AGE_NAME = "Access-Control-Max-Age";
 public static final String EXPOSE_HEADER = "Access-Control-Expose-Headers";
 public static final String CONTENT_TYPE = "Content-Type";
 
  private static final String ALLOWED_CREDENTIALS = "*";
  private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, role";
  private static final String ALLOWED_METHODS = "GET, POST";
  private static final String ALLOWED_ORIGIN = "*";
  private static final String MAX_AGE = "3600";
  private static final String ALLOWED_EXPOSED_HEADERS = "Authorization, role";
  private static final String ALLOWED_CONTENT_TYPE = "application/json";
 

  @Bean
  public WebFilter corsFilter() {
    return (ServerWebExchange ctx, WebFilterChain chain) -> {
      ServerHttpRequest request = ctx.getRequest();
      if (CorsUtils.isCorsRequest(request)) {
        ServerHttpResponse response = ctx.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add(ORIGIN_NAME, ALLOWED_ORIGIN);
        headers.add(METHODS_NAME, ALLOWED_METHODS);
        headers.add(MAX_AGE_NAME, MAX_AGE);
        headers.add(HEADERS_NAME, ALLOWED_HEADERS);
        headers.add(EXPOSE_HEADER, ALLOWED_EXPOSED_HEADERS);
        headers.add(CREDENTIALS_NAME, ALLOWED_CREDENTIALS);
		headers.add(CONTENT_TYPE, ALLOWED_CONTENT_TYPE);
        if (request.getMethod() == HttpMethod.OPTIONS) {
          response.setStatusCode(HttpStatus.OK);
          return Mono.empty();
        }
      }
      return chain.filter(ctx);
    };
  }

}
