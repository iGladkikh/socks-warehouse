package com.igladkikh.warehouse.controller;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.model.SockColor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.System.getProperty;

@Validated
@RestController
@RequestMapping("/api/socks")
public class SockController {

    @GetMapping
    public int getCount(@RequestParam SockColor[] color, @RequestParam String cotton) {
        return 0;
    }

    @PostMapping("/income")
    public void plus(@RequestBody @Valid SockDto dto) {

    }

    @PostMapping("/outcome")
    public void minus(@RequestBody @Valid SockDto dto) {
        return;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable @Positive long id, @RequestBody @Valid SockDto dto) {
        return;
    }

    @PostMapping("/batch")
    public void uploadFromFile(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        if (file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty");
        }

        String mimeType = file.getContentType();
        if (!"text/csv".equals(mimeType)) {
            throw new RuntimeException("Invalid uploaded file mime type");
        }

        try (Scanner scanner = new Scanner(file.getInputStream())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");

                SockDto sockDto = new SockDto();
                sockDto.setColor(SockColor.valueOf(split[0]));
                sockDto.setCotton(Integer.parseInt(split[1]));
                sockDto.setQuantity(Integer.parseInt(split[2]));

                plus(sockDto);
            }

            // Сохранение копии файла
            Path tmpDirectory = Paths.get(getProperty("user.dir"), "tmp");
            if (Files.notExists(tmpDirectory)) {
                Files.createDirectories(tmpDirectory);
            }
            Path tmpFile = Files.createTempFile(tmpDirectory, "upload", ".tmp");
            file.transferTo(tmpFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
