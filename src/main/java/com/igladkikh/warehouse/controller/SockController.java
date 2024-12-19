package com.igladkikh.warehouse.controller;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockFilter;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.service.SockService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class SockController {
    private final SockService service;

    @GetMapping
    public List<SockDto> findMany(@RequestParam SockColor[] colors,
                                  @RequestParam(defaultValue = "0") int cottonMin,
                                  @RequestParam(defaultValue = "100") int cottonMax,
                                  @RequestParam(defaultValue = SockFilter.DEFAULT_ORDER_FIELD) SockFilter.OrderField orderField,
                                  @RequestParam(defaultValue = SockFilter.DEFAULT_ORDER_DIRECTION) SockFilter.OrderDirection orderDirection) {

        SockFilter filter = SockFilter.builder()
                .color(colors)
                .cottonMax(cottonMax)
                .cottonMin(cottonMin)
                .orderField(orderField)
                .orderDirection(orderDirection)
                .build();
        return service.findMany(filter);
    }

    @PostMapping("/income")
    public SockDto plus(@RequestBody @Valid SockDto dto) {
        return service.plus(dto);
    }

    @PostMapping("/outcome")
    public SockDto minus(@RequestBody @Valid SockDto dto) {
        return service.minus(dto);
    }

    @PutMapping("/{id}")
    public SockDto update(@PathVariable @Min(1) long id,
                          @RequestBody @Valid SockDto dto) {
        return service.update(id, dto);
    }

    @PostMapping("/batch")
    public void uploadFromFile(@RequestParam("file") MultipartFile file) {
        service.uploadFromFile(file);
    }
}
