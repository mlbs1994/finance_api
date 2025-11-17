package com.mlbs.finance_api.utils;

import java.lang.reflect.Field;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static <T> boolean isDtoEmpty(T dto) {
        if (dto == null) return true;

        Class<?> clazz = dto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(dto);

                if (value != null) {
                    if (!(value instanceof String str && str.isBlank())) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar campos via reflexão", e);
        }

        return true;
    }
}
