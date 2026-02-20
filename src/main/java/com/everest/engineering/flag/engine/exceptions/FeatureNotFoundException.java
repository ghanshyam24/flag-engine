package com.everest.engineering.flag.engine.exceptions;

public class FeatureNotFoundException extends RuntimeException {
    public FeatureNotFoundException(String name) {
        super("Feature not found: " + name);
    }
}
