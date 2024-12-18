package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.model.SockColor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record SockFilter(SockColor[] color, int quantity) {
}
