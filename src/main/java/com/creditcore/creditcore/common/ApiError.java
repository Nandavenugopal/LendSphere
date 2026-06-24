package com.creditcore.creditcore.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiError {
    private String message;
    private LocalDateTime timestamp;
}