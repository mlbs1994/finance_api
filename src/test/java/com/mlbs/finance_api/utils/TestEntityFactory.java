package com.mlbs.finance_api.utils;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class TestEntityFactory {

    public <D, R> R createEntity(D dto, Function<D, R> creator) {
        return creator.apply(dto);
    }
}
