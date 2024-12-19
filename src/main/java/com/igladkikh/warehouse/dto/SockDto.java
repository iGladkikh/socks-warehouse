package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.annotation.PercentValue;
import com.igladkikh.warehouse.model.SockColor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class SockDto {
    private long id;
    @NotNull
    private SockColor color;
    @PercentValue
    private int cotton;
    @PositiveOrZero
    private int quantity;
}
