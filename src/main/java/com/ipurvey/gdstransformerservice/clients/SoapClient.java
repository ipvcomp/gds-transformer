package com.ipurvey.gdstransformerservice.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SoapClient implements Client {



    
    @Override
    public <T> ResponseEntity<T> call(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
         throw new UnsupportedOperationException("SOAP call method is not supported for REST clients.");
    }

    public <T> ResponseEntity<T> call(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return null;
    }
}
