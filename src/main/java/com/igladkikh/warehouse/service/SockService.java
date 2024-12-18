package com.igladkikh.warehouse.service;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockFilter;

public interface SockService {

    int getCount(SockFilter filter);

    SockDto plus(SockDto dto);

    SockDto minus(SockDto dto);

    SockDto update(long id);

    void uploadFromFile();
}
