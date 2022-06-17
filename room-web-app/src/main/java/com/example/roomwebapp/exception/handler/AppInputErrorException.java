package com.example.roomwebapp.exception.handler;

import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ToString
public class AppInputErrorException extends IllegalArgumentException {

    public AppInputErrorException() {
    }

    private Integer objectIndex;

    private List<String> errors;

    public AppInputErrorException(String s, Object... args) {
        super(Translator.toLocale(s, args));
    }

    public AppInputErrorException(Integer objectIndex, String s, Object... args) {
        super(Translator.toLocale(s, args));
        this.objectIndex = objectIndex;
    }

    public AppInputErrorException(String message, Throwable cause, Object... args) {
        super(Translator.toLocale(message, args), cause);
    }

    public AppInputErrorException(List<String> messages) {
        super();
        this.errors = messages;
    }

    public AppInputErrorException(List<String> messages, String error) {
        super();
        if (messages == null && error != null) {
            this.errors = Collections.singletonList(error);
        } else if (messages != null) {
            this.errors = messages;
        }
    }

    public AppInputErrorException(Throwable cause) {
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
