package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.annotation.PercentValue;
import com.igladkikh.warehouse.model.SockColor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SockDto {
    private long id;
    @NotNull
    private SockColor color;
    @PercentValue
    private int cotton;
    @Min(0)
    private int quantity;
}
