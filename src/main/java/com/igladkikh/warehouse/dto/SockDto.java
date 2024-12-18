package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.model.SockColor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record SockDto(
        long id,
        @NotNull
        SockColor color,
        @Min(0)
        @Max(100)
        int cotton,
        @PositiveOrZero
        int quantity) {
}
