package com.example.todo.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.List;

public class MvcUtils {

    public static <T> T convertObject(MvcResult result, Class<T> responseClass) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(contentAsString, responseClass);
    }

    public static <T> List<T> convertList(MvcResult result, Class<T> clazz) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(contentAsString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

}
