package com.example.roomwebapp.exception.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class AppResponse {
    private List<String> messages;

    public AppResponse() {
    }

    public AppResponse(List<String> messages) {
        this.messages = messages;
    }

    public AppResponse(String message) {
        this.messages = Collections.singletonList(message);
    }
}
