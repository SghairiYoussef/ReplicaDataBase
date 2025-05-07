package com.sysrep.replicas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class Reader {

    @Value("${replica.file.path}")
    private String filePath;

    public List<String> readAllLines() throws IOException {
        if (!Files.exists(Paths.get(filePath))) return Collections.emptyList();
        return Files.readAllLines(Paths.get(filePath));
    }

    public String readLastLine() throws IOException {
        List<String> lines = readAllLines();
        return lines.isEmpty() ? "" : lines.getLast();
    }
}
