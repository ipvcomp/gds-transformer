package com.ipurvey.gdstransformerservice.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


public interface Client {
    <T> ResponseEntity<T> call(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType);
}
