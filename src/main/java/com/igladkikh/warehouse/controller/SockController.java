package com.igladkikh.warehouse.controller;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.model.SockColor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SockController {

    @GetMapping
    public int getCount(@RequestParam SockColor[] color, @RequestParam String cotton) {
        return 0;
    }

    @PostMapping("/income")
    public void plus(SockDto dto) {
        return;
    }

    @PostMapping("/outcome")
    public void minus(SockDto dto) {
        return;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id) {
        return;
    }

    @PostMapping("/batch")
    public void uploadFromFile() {
        return;
    }
}
