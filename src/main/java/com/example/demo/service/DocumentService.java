
package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }
}
