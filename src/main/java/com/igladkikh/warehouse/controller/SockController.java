package com.igladkikh.warehouse.controller;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.service.SockService;
import com.igladkikh.warehouse.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Sock-controller")
@Validated
@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class SockController {
    private final SockService service;

    @Operation(
            summary = "Получение общего количества носков с фильтрацией",
            description = "Возвращает количество носков, соответствующих критериям"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockDto.class)))
                    })
    })
    @GetMapping
    public List<SockDto> findMany(@Parameter(description = "Цвет")
                                  @RequestParam(required = false, name = "color") List<SockColor> colors,
                                  @Parameter(description = "Содержание хлопка (равно)")
                                  @RequestParam(required = false) Integer cotton,
                                  @Parameter(description = "Содержание хлопка (от)")
                                  @RequestParam(required = false) Integer cottonMin,
                                  @Parameter(description = "Содержание хлопка (до)")
                                  @RequestParam(required = false) Integer cottonMax,
                                  @Parameter(description = "Поле для сортировки")
                                  @RequestParam(defaultValue = SockQueryFilter.DEFAULT_ORDER_FIELD) SockQueryFilter.SortField sortField,
                                  @Parameter(description = "Направление сортировки")
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

    @Operation(summary = "Регистрация прихода носков", description = "Увеличивает количество носков на складе")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Возвращается обновленная сущность с учетом прихода",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SockDto.class))
                    })
    })
    @PostMapping(value = "/income")
    public SockDto plus(@RequestBody @Valid SockDto dto) {
        return service.plus(dto);
    }

    @Operation(summary = "Регистрация отпуска носков", description = "Уменьшает количество носков на складе, если их хватает")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Возвращается обновленная сущность с учетом отпуска",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SockDto.class))
                    })
    })
    @PostMapping("/outcome")
    public SockDto minus(@RequestBody @Valid SockDto dto) {
        return service.minus(dto);
    }

    @Operation(summary = "Обновление данных носков", description = "Позволяет изменить параметры носков (цвет, процент хлопка, количество)")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Возвращается обновленная сущность",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SockDto.class))
                    })
    })
    @PutMapping("/{id}")
    public SockDto update(@PathVariable @Min(1) long id,
                          @RequestBody @Valid SockDto dto) {
        return service.update(id, dto);
    }

    @Operation(
            summary = "Загрузка партий носков из CSV",
            description = "Принимает CSV файл с партиями носков, содержащими цвет, процентное содержание хлопка и количество.<br/>" +
                    "Формат файла - <b>src/main/resources/static/socks_upload.csv</b>"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Возвращается список всех сущностей с учетом обновлений",
                    responseCode = "200"
            )
    })
    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<SockDto> uploadFromFile(@RequestPart("file") MultipartFile file) {
        FileUtil.validate(file);
        return service.uploadFromFile(file);
    }
}
