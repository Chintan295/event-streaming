package com.datasolutions.eventstreaming.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T>{
    private HttpStatus status;
    private  T data;
    private String errorMessage;
}
