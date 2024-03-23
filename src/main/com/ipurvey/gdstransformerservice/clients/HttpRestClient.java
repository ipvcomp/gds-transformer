package com.ipurvey.gdstransformerservice.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service(value = "httpClient")
public class HttpRestClient implements Client {

    private final RestTemplate restClient = new RestTemplate();


    @Override
    public <T> ResponseEntity<T> call(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restClient.exchange( url, method,requestEntity,responseType, new HashMap<>());

    }
}
