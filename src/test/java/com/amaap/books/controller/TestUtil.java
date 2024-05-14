package com.amaap.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
public class TestUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String asJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}