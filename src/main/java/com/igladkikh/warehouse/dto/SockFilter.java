package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.model.SockColor;
import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Builder
public class SockFilter {
    public static final String DEFAULT_ORDER_FIELD = "ID";
    public static final String DEFAULT_ORDER_DIRECTION = "ASC";

    private SockColor[] color;
    private Integer cottonMin;
    private Integer cottonMax;
    private Integer quantity;
    private OrderField orderField;
    private OrderDirection orderDirection;

    public enum OrderField {
        ID,
        COLOR,
        COTTON,
        QUANTITY
    }

    public enum OrderDirection {
        ASC,
        DESC
    }
}
