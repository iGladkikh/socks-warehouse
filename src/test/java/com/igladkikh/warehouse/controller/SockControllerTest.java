package com.igladkikh.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import com.igladkikh.warehouse.mapper.SockMapper;
import com.igladkikh.warehouse.model.Sock;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.service.SockService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.criteria.CriteriaBuilder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SockController.class)
class SockControllerTest {
    private static final String URI_PATH = "/api/socks";
    @MockBean
    private SockService sockService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    private static SockDto sockDto;
    private static Sock sock;

    @BeforeAll
    static void setUp() {
        SockColor colorValue = SockColor.BLACK;
        int cottonValue = 20;
        int quantityValue = 50;

        sockDto = new SockDto();
        sockDto.setColor(colorValue);
        sockDto.setCotton(cottonValue);
        sockDto.setQuantity(quantityValue);

        sock = SockMapper.toEntity(sockDto);
        sock.setId(1);
    }

    @Test
    void findMany() throws Exception {
        when(sockService.findWithFilter(any(SockQueryFilter.class)))
                .thenReturn(List.of(sockDto));

        mvc.perform(get(URI_PATH)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(sockDto.getId()), Long.class));
    }

    @Test
    void plus() throws Exception {

        when(sockService.plus(any(SockDto.class)))
                .thenReturn(sockDto);

        mvc.perform(post(URI_PATH + "/income")
                        .content(mapper.writeValueAsString(sockDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sockDto.getId()), Long.class))
                .andExpect(jsonPath("$.color", is(sockDto.getColor().toString()), SockColor.class))
                .andExpect(jsonPath("$.cotton", is(sockDto.getCotton()), Integer.class))
                .andExpect(jsonPath("$.quantity", is(sockDto.getQuantity()), Integer.class));

        verify(sockService, times(1)).plus(sockDto);
    }

    @Test
    void minus() throws Exception {

        when(sockService.minus(any(SockDto.class)))
                .thenReturn(sockDto);

        mvc.perform(post(URI_PATH + "/outcome")
                        .content(mapper.writeValueAsString(sockDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sockDto.getId()), Long.class))
                .andExpect(jsonPath("$.color", is(sockDto.getColor().toString()), SockColor.class))
                .andExpect(jsonPath("$.cotton", is(sockDto.getCotton()), Integer.class))
                .andExpect(jsonPath("$.quantity", is(sockDto.getQuantity()), Integer.class));

        verify(sockService, times(1)).minus(sockDto);
    }

    @Test
    void update() throws Exception {

        when(sockService.update(anyLong(), any(SockDto.class)))
                .thenReturn(sockDto);

        mvc.perform(put(URI_PATH + "/" + sock.getId())
                        .content(mapper.writeValueAsString(sockDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sockDto.getId()), Long.class))
                .andExpect(jsonPath("$.color", is(sockDto.getColor().toString()), SockColor.class))
                .andExpect(jsonPath("$.cotton", is(sockDto.getCotton()), Integer.class))
                .andExpect(jsonPath("$.quantity", is(sockDto.getQuantity()), Integer.class));

        verify(sockService, times(1)).update(1, sockDto);
    }
}