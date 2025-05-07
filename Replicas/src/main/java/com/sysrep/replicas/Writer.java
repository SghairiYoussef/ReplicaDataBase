package com.sysrep.replicas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class Writer {

    @Value("${replica.file.path}")
    private String filePath;

    public synchronized void appendLine(String line) throws IOException {
        System.out.println(filePath);
        Files.createDirectories(Paths.get(filePath).getParent());
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(line + System.lineSeparator());
        }
    }
}
