import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class DocumentUploader {

    public static class Document {
        private String topic;
        private Date date;
        private String organization;
        private String content;

        public Document(String topic, Date date, String organization, String content) {
            this.topic = topic;
            this.date = date;
            this.organization = organization;
            this.content = content;
        }

        // Getters and toString() for debugging
        public String getTopic() {
            return topic;
        }

        public Date getDate() {
            return date;
        }

        public String getOrganization() {
            return organization;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "Document{" +
                    "topic='" + topic + '\'' +
                    ", date=" + date +
                    ", organization='" + organization + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static Document uploadDocument(File file, String topic, Date date, String organization) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file provided.");
        }

        // Extract text content from the file
        String content = new String(Files.readAllBytes(file.toPath()));

        // Create a Document object
        return new Document(topic, date, organization, content);
    }

    public static void main(String[] args) {
        try {
            // Example usage
            File file = new File("example.txt"); // Replace with your file path
            String topic = "Technology";
            Date date = new Date(); // Current date
            String organization = "TechCorp";

            Document document = uploadDocument(file, topic, date, organization);
            System.out.println("Document uploaded successfully:");
            System.out.println(document);
        } catch (Exception e) {
            System.err.println("Error uploading document: " + e.getMessage());
        }
    }
}



/**
    Основные компоненты:
Класс Document:
Представляет документ с полями:
topic — тема документа.
date — дата, связанная с документом.
organization — организация, связанная с документом.
content — текстовое содержимое документа.
Содержит конструктор, геттеры и метод toString() для удобного вывода информации.
Метод uploadDocument:

Принимает файл (File), тему (topic), дату (date) и организацию (organization) в качестве параметров.
Проверяет, существует ли файл и является ли он файлом.
Читает содержимое файла с помощью Files.readAllBytes() и преобразует его в строку.
Создает объект Document с переданными параметрами и содержимым файла.
Метод main:

Демонстрирует пример использования метода uploadDocument.
Загружает файл, создает объект Document и выводит его в консоль.
*/