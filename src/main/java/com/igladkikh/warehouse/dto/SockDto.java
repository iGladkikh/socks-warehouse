package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.annotation.PercentValue;
import com.igladkikh.warehouse.model.SockColor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Сущность товара")
public class SockDto {
    @Schema(description = "Уникальный идентификатор товара", example = "314", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;
    @NotNull
    @Schema(description = "Цвет носков")
    private SockColor color;
    @Schema(description = "Содержание хлопка в процентах", example = "70", requiredMode = Schema.RequiredMode.REQUIRED)
    @PercentValue
    private int cotton;
    @Schema(description = "Отстаток на складе", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(0)
    private int quantity;
}
