package com.ms.salesanalysis.aiSalesAnalysis.controller;

import com.ms.salesanalysis.aiSalesAnalysis.service.ExcelProcessingService;
import com.ms.salesanalysis.aiSalesAnalysis.service.LlmService;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ExcelProcessingService excelProcessingService;
    private final LlmService llmService;

    public ChatController(ExcelProcessingService excelProcessingService, LlmService llmService) {
        this.excelProcessingService = excelProcessingService;
        this.llmService = llmService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Please upload a file!"));
        }

        try {
            List<Document> documents = excelProcessingService.processExcelFile(file);

            llmService.addDocumentsToVectorStore(documents);
            return ResponseEntity.ok(Map.of("message", "File uploaded and processed successfully!"));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("message", "Failed to process file: " + e.getMessage()));
        }
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query cannot be empty"));
        }
        String response = llmService.chat(query);
        return ResponseEntity.ok(Map.of("response", response));
    }
}

