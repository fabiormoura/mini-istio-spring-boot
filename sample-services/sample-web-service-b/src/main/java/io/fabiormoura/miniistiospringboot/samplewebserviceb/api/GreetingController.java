package io.fabiormoura.miniistiospringboot.samplewebserviceb.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GreetingController {
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() {
        return new Greeting(UUID.randomUUID().toString(), "hello!");
    }
}
