package com.mlbs.finance_api.config.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {
	
    RESOURCE_NOT_FOUND("/errors/resource-not-found"),
    BAD_REQUEST("/errors/bad-request"),
    INTERNAL_SERVER_ERROR("/errors/internal-server-error");

    private final String uri;

    ErrorType(String uri) {
        this.uri = uri;
    }

    @JsonValue
    public String getUri() {
        return uri;
    }
    
}