package com.example.todo.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

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

    public static void checkReturnedErrors(MvcResult result, String... errors) throws IOException {
        List<String> errorMessages = Arrays.asList(errors);
        List<String> resultErrorMessages = JsonPath.read(result.getResponse().getContentAsString(), "validationErrors");

        assertEquals(resultErrorMessages.size(), errorMessages.size());

        for(String errorMessage : errorMessages){
            List<String> locatedMessages = resultErrorMessages.stream()
                    .filter(errorMessage::equals)
                    .collect(Collectors.toList());

            assertFalse(locatedMessages.isEmpty());
            assertFalse(locatedMessages.size() > 1);
        }
    }

}
