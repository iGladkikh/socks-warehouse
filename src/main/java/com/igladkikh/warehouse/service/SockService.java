package com.igladkikh.warehouse.service;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SockService {

    List<SockDto> findWithFilter(SockQueryFilter filter);

    SockDto plus(SockDto dto);

    SockDto minus(SockDto dto);

    SockDto update(long id, SockDto dto);

    void uploadFromFile(MultipartFile file);
}
