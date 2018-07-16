package io.fabiormoura.miniistiospringboot.samplewebservicea.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class InternalProxyController {
    private final String sampleWebServiceBEndpoint;
    private final RestTemplate restTemplate;

    @Autowired
    public InternalProxyController(@Value("${sample-web-service-b.endpoint}") String sampleWebServiceBEndpoint, RestTemplate restTemplate) {
        this.sampleWebServiceBEndpoint = sampleWebServiceBEndpoint;
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/proxy/greeting", method = RequestMethod.GET)
    public ResponseEntity<String> greeting() {
        return restTemplate.getForEntity(String.format("%s/greeting", sampleWebServiceBEndpoint), String.class);
    }
}
