package com.igladkikh.warehouse.mapper;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.model.Sock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SockMapper {

    public static Sock toEntity(SockDto dto) {
        Sock sock = new Sock();
        sock.setColor(dto.color());
        sock.setCottonPercentPart(dto.cotton());
        sock.setQuantity(dto.quantity());
        return sock;
    }

    public static SockDto toDto(Sock sock) {
        return new SockDto(sock.getId(),
                sock.getColor(),
                sock.getCottonPercentPart(),
                sock.getQuantity());
    }

    public static List<SockDto> toDto(Iterable<Sock> socks) {
        if (socks == null) {
            return Collections.emptyList();
        }

        List<SockDto> dtos = new ArrayList<>();
        for (Sock sock : socks) {
            dtos.add(toDto(sock));
        }
        return dtos;
    }
}
