package com.tui.dwh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.dwh.erdplus.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModelParser {

    public Model parse(Path path) throws IOException {
        byte[] model = Files.readAllBytes(path);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(model, objectMapper.constructType(Model.class));
    }
}
