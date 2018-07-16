package io.fabiormoura.miniistiospringboot.samplewebserviceb.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    @JsonProperty
    private String id;
    @JsonProperty
    private String content;

    public Greeting() {
    }

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
    }
}