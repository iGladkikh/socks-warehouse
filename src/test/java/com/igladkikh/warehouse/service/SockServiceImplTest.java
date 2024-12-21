package com.igladkikh.warehouse.service;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.repository.SockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles({"test"})
class SockServiceImplTest {
    @Autowired
    private SockService sockService;
    @Autowired
    private SockRepository repository;
    private static SockDto sockDto;

    @BeforeAll
    static void setUp() {
        sockDto = new SockDto();
        sockDto.setColor(SockColor.BLACK);
        sockDto.setCotton(20);
        sockDto.setQuantity(50);
    }

    @BeforeEach
    void clearBD() {
        repository.deleteAll();
    }

    @Test
    void findWithFilter() {
        sockService.plus(SockServiceImplTest.sockDto);
        SockQueryFilter queryFilter = SockQueryFilter.builder()
                .sortField(SockQueryFilter.SortField.ID)
                .build();
        List<SockDto> result = sockService.findWithFilter(queryFilter);

        assertEquals(1, result.size());
    }

    @Test
    void plus() {
        SockDto result = sockService.plus(SockServiceImplTest.sockDto);

        assertTrue(result != null);
        assertEquals(sockDto.getColor(), result.getColor());
        assertEquals(sockDto.getCotton(), result.getCotton());
        assertEquals(sockDto.getQuantity(), result.getQuantity());
    }

    @Test
    void minus() {
        sockService.plus(SockServiceImplTest.sockDto);
        SockDto result = sockService.minus(SockServiceImplTest.sockDto);

        assertTrue(result != null);
        assertEquals(sockDto.getColor(), result.getColor());
        assertEquals(sockDto.getCotton(), result.getCotton());
        assertEquals(result.getQuantity(), 0);
    }

    @Test
    void update() {
        SockDto created = sockService.plus(SockServiceImplTest.sockDto);
        created.setQuantity(5000);

        SockDto result = sockService.update(created.getId(), created);

        assertTrue(result != null);
        assertEquals(result.getQuantity(), 5000);
    }
}