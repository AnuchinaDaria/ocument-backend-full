package com.example.document.service;

import com.example.document.model.Document;
import com.example.document.repository.DocumentRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(MultipartFile file, String title, String organization, String category) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);

        Tika tika = new Tika();
        String content = "";

        try {
            content = tika.parseToString(filePath.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document document = new Document();
        document.setTitle(title);
        document.setOrganization(organization);
        document.setCategory(category);
        document.setUploadDate(LocalDate.now());
        document.setFilePath(filePath.toString());
        document.setContent(content);

        return documentRepository.save(document);

    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}