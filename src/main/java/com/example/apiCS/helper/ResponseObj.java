package com.example.apiCS.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObj {
    @JsonProperty("status_code")
    private int statusCode;
    private boolean success;
    private String message;
    private Object data;

}
