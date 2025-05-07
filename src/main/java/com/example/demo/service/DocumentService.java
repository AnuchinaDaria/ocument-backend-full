package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final Path uploadPath = Paths.get("uploads");

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(MultipartFile file, String title, String organization, String category) {
        try {
            // Создаем папку, если ее нет
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Сохраняем файл на диск
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            // Читаем текст из файла (ловим оба исключения)
            Tika tika = new Tika();
            String content;
            try {
                content = tika.parseToString(Files.newInputStream(filePath));
            } catch (IOException | TikaException e) {
                content = "Пустой файл или не удалось прочитать содержимое.";
            }

            // Если title не указан — берем имя файла
            if (title == null || title.isEmpty()) {
                title = file.getOriginalFilename();
            }

            // Создаем и заполняем документ
            Document document = new Document();
            document.setTitle(title);
            document.setOrganization(organization);
            document.setCategory(category);
            document.setUploadDate(LocalDateTime.now());
            document.setFilePath(filePath.toString());
            document.setContent(content);
            document.setFileName(file.getOriginalFilename());

            return documentRepository.save(document);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public java.util.List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}