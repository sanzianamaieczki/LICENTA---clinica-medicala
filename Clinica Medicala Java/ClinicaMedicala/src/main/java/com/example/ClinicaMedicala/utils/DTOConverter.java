package com.example.ClinicaMedicala.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class DTOConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> convertToMap(Object dto) {
        return objectMapper.convertValue(dto, Map.class);
    }
}
