package com.everest.engineering.flag.engine.exceptions;

public class FeatureAlreadyExistsException extends RuntimeException {
    public FeatureAlreadyExistsException(String name) {
        super("Feature already exists: " + name);
    }
}
