package com.dreamli.greeting.service;

import com.dreamli.greeting.properties.GreetingProperties;

public class GreetingService {

    private GreetingProperties greetingProperties;

    public String greeting(String name) {
        return greetingProperties.getPrefix() + ", " + name
                + ", " + greetingProperties.getSuffix();
    }

    public GreetingProperties getGreetingProperties() {
        return greetingProperties;
    }

    public void setGreetingProperties(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }

}
