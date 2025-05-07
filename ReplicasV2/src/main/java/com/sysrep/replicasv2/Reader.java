package com.sysrep.replicasv2;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component
public class Reader {

    public List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) return Collections.emptyList();
        return Files.readAllLines(path);
    }

    public String readLastLine(String filePath) throws IOException {
        List<String> lines = readAllLines(filePath);
        return lines.isEmpty() ? "" : lines.getLast();
    }
}
