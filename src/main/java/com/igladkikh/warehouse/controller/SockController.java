package com.igladkikh.warehouse.controller;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.service.SockService;
import com.igladkikh.warehouse.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    public List<SockDto> findMany(@RequestParam(required = false, name = "color") List<SockColor> colors,
                                  @RequestParam(required = false) Integer cotton,
                                  @RequestParam(required = false) Integer cottonMin,
                                  @RequestParam(required = false) Integer cottonMax,
                                  @RequestParam(defaultValue = SockQueryFilter.DEFAULT_ORDER_FIELD) SockQueryFilter.SortField sortField,
                                  @RequestParam(defaultValue = SockQueryFilter.DEFAULT_ORDER_DIRECTION) Sort.Direction sortDirection) {

        SockQueryFilter filter = SockQueryFilter.builder()
                .colors(colors)
                .cotton(cotton)
                .cottonMax(cottonMax)
                .cottonMin(cottonMin)
                .sortField(sortField)
                .sortDirection(sortDirection)
                .build();
        return service.findWithFilter(filter);
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
        FileUtil.validate(file);
        service.uploadFromFile(file);
    }
}
