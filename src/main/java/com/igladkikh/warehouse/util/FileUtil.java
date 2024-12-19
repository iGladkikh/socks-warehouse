package com.igladkikh.warehouse.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;

public class FileUtil {
    private static final String MIME_TYPE = "text/csv";
    private static final Path TMP_DIRECTORY = Paths.get(getProperty("user.dir"), "tmp");

    public static void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty");
        }

        String mimeType = file.getContentType();
        if (!MIME_TYPE.equals(mimeType)) {
            throw new RuntimeException("Invalid uploaded file mime type");
        }
    }

    public static Path createTempFile(MultipartFile file) throws IOException {
        if (Files.notExists(TMP_DIRECTORY)) {
            Files.createDirectories(TMP_DIRECTORY);
        }
        Path tmpFile = Files.createTempFile(TMP_DIRECTORY, "upload", ".tmp");
        file.transferTo(tmpFile);
        return tmpFile;
    }
}
