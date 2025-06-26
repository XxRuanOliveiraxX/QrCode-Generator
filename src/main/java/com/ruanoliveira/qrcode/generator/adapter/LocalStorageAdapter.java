package com.ruanoliveira.qrcode.generator.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ruanoliveira.qrcode.generator.ports.StoragePort;

import jakarta.annotation.PostConstruct;

@Component
public class LocalStorageAdapter implements StoragePort {

    @Value("${storage.local.path:./uploads}")
    private String storagePath;

    @Value("${storage.local.base-url:http://localhost:8080/files}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        // Criar diretório de upload se não existir
        try {
            Path uploadPath = Paths.get(storagePath);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório de upload", e);
        }
    }

    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        try {
            Path filePath = Paths.get(storagePath, fileName);
            Files.write(filePath, fileData);
            
            return String.format("%s/%s", baseUrl, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo localmente", e);
        }
    }
} 