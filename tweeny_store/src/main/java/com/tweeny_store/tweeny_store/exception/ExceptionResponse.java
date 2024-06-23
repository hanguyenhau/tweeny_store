package com.tweeny_store.tweeny_store.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private Integer bussinessCode;
    private String bussinessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
