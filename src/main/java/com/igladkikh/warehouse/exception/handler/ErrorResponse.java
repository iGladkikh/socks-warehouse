package com.igladkikh.warehouse.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(String status,
                            String message,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                            LocalDateTime timestamp) {
}
