package com.ipurvey.gdstransformerservice.clients;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Map;

@Service
public class HttpRestClient implements Client {

    private final RestClient restClient = RestClient.create();


    @Override
    public <T> ResponseEntity<T> call(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restClient.method(method)
                .uri(URI.create(url))
                .body(requestEntity).
                contentType(MediaType.APPLICATION_JSON).
                 retrieve().toEntity(responseType);
    }
}
