package com.example.roomwebapp.exception.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AppInputFieldErrorsException extends IllegalArgumentException {

    public AppInputFieldErrorsException() {
    }

    private Integer objectIndex;
    private List<String> errors;

    public AppInputFieldErrorsException(String s, Object... args) {
        super(Translator.toLocale(s, args));
    }

    public AppInputFieldErrorsException(Integer objectIndex, List<String> errors) {
        this.objectIndex = objectIndex;
        this.errors = errors;
    }

    public AppInputFieldErrorsException(Integer objectIndex, String s, Object... args) {
        super(Translator.toLocale(s, args));
        this.objectIndex = objectIndex;
    }

    public AppInputFieldErrorsException(String message, Throwable cause, Object... args) {
        super(Translator.toLocale(message, args), cause);
    }

    public AppInputFieldErrorsException(Throwable cause) {
        super(cause);
    }

    public Map<String, Integer> getData() {
        if (this.objectIndex != null) {
            return Collections.singletonMap("index", objectIndex);
        }
        return null;
    }

    public List<String> getErrors() {
        return errors;
    }
}
