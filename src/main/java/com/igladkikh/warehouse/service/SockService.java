package com.igladkikh.warehouse.service;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockFilter;
import org.springframework.web.multipart.MultipartFile;

public interface SockService {

    int getCount(SockFilter filter);

    SockDto plus(SockDto dto);

    SockDto minus(SockDto dto);

    SockDto update(long id, SockDto dto);

    void uploadFromFile(MultipartFile file);
}
