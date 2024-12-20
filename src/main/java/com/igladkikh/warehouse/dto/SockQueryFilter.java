package com.igladkikh.warehouse.dto;

import com.igladkikh.warehouse.model.SockColor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Builder
@Getter
public class SockQueryFilter {
    public static final String DEFAULT_ORDER_FIELD = "ID";
    public static final String DEFAULT_ORDER_DIRECTION = "ASC";

    private List<SockColor> colors;
    private Integer cotton;
    private Integer cottonMin;
    private Integer cottonMax;
    private Integer quantity;
    private SortField sortField;
    private Sort.Direction sortDirection;

    public enum SortField {
        ID,
        COLOR,
        COTTON,
        QUANTITY
    }
}
