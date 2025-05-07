package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PostMapping
    public Document uploadDocument(@RequestParam("file") MultipartFile file,
                                   @RequestParam("title") String title,
                                   @RequestParam("organization") String organization,
                                   @RequestParam("category") String category) {
        return documentService.saveDocument(file, title, organization, category);
    }

    @GetMapping("/{id}")
    public Document getDocument(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }
}
