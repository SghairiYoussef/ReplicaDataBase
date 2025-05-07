package com.sysrep.replicasv2;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Writer {

    public void appendLine(String filePath, String line) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(line + System.lineSeparator());
        }
    }
}