package com.synerset.lottery.infrastructure.objectmappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synerset.lottery.infrastructure.exceptions.objectmappers.JsonProcessingFailureException;

import java.io.IOException;

public class JsonConverters {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public static <K> K convertJsonResponseToTargetObject(String responseAsJson, Class<K> targetClass) {
        try {
            return objectMapper.readValue(responseAsJson, targetClass);
        } catch (IOException e) {
            throw new JsonProcessingFailureException(String.format("Could not read: %s \nto object of class: %s", responseAsJson, targetClass));
        }
    }

    public static String writeObjectAsJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingFailureException(String.format("Could not parse: %s \nto Json as string", object.getClass()));
        }
    }

}
