package io.fabiormoura.miniistiospringboot.samplewebservicea.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExternalProxyController {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/proxy/google", method = RequestMethod.GET)
    public ResponseEntity<String> google() {
        return restTemplate.getForEntity("https://www.google.com/", String.class);
    }
}
