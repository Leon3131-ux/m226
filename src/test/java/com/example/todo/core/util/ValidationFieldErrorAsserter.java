package com.example.todo.core.util;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@AllArgsConstructor
public class ValidationFieldErrorAsserter {

    @NonNull
    private final Validator validator;
    @NonNull
    private final Errors errors;

    public void assertFieldHasError(Object toValidate, String fieldName, String errorCode){
        validate(toValidate);
        assertErrorSet(fieldName, errorCode);
    }

    private void validate(Object toValidate) {
        validator.validate(toValidate, errors);
    }

    private void assertErrorSet(String fieldName, String errorCode) {
        assertNotNull("No error for field '" + fieldName + "' set", errors.getFieldError(fieldName));
        assertEquals(1, errors.getFieldErrors().stream().filter(e -> Objects.equals(e.getCode(), errorCode)).count());
        assertNotNull("No error code for field '" + fieldName + "' set", errors.getFieldError(fieldName).getCode());
    }

}
