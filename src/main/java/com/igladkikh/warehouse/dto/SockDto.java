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
    @PercentValue(message = "Значение поля cotton должно быть в диапазоне от 0 до 100")
    private int cotton;
    @Schema(description = "Остаток на складе", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "Значение поля quantity не должно быть отрицательным")
    private int quantity;
}
