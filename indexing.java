import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Indexing {

    private static final String ELASTICSEARCH_INDEX = "documents";
    private static final String POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String POSTGRESQL_USER = "your_user";
    private static final String POSTGRESQL_PASSWORD = "your_password";

    private RestHighLevelClient elasticsearchClient;
    private Connection postgresConnection;

    public Indexing(RestHighLevelClient elasticsearchClient) throws SQLException {
        this.elasticsearchClient = elasticsearchClient;
        this.postgresConnection = DriverManager.getConnection(POSTGRESQL_URL, POSTGRESQL_USER, POSTGRESQL_PASSWORD);
    }

    public void processFile(String filePath, String title, String date, String source) throws IOException, SQLException {
        // Read file content
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // Index content in Elasticsearch
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("title", title);
        jsonMap.put("date", date);
        jsonMap.put("source", source);
        jsonMap.put("content", content);

        IndexRequest indexRequest = new IndexRequest(ELASTICSEARCH_INDEX)
                .source(jsonMap, XContentType.JSON);
        elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT);

        // Store metadata in PostgreSQL
        String sql = "INSERT INTO documents (title, date, source, file_path) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = postgresConnection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, date);
            statement.setString(3, source);
            statement.setString(4, filePath);
            statement.executeUpdate();
        }
    }

    public void close() throws IOException, SQLException {
        if (elasticsearchClient != null) {
            elasticsearchClient.close();
        }
        if (postgresConnection != null) {
            postgresConnection.close();
        }
    }

    public static void main(String[] args) {
        // Example usage
        try (RestHighLevelClient elasticsearchClient = new RestHighLevelClient(
                org.elasticsearch.client.RestClient.builder(
                        new org.apache.http.HttpHost("localhost", 9200, "http")))) {

            Indexing indexing = new Indexing(elasticsearchClient);
            indexing.processFile("path/to/your/file.txt", "Document Title", "2023-10-01", "Source Name");
            indexing.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/**
Чтение содержимого файла:

Метод processFile считывает содержимое текстового файла, используя Files.readAllBytes(Paths.get(filePath)).
Индексирование в Elasticsearch:

Содержимое файла вместе с метаданными (название, дата, источник) преобразуется в JSON-объект.
Этот объект отправляется в Elasticsearch для индексирования с использованием клиента RestHighLevelClient.
Сохранение метаданных в PostgreSQL:

Метаданные файла (название, дата, источник, путь к файлу) сохраняются в таблице documents базы данных PostgreSQL с помощью SQL-запроса INSERT.
Управление подключениями:

Класс создает подключения к Elasticsearch и PostgreSQL в конструкторе.
Метод close закрывает эти подключения, чтобы избежать утечек ресурсов.
Пример использования:

В методе main демонстрируется пример использования класса: создается клиент Elasticsearch, вызывается метод processFile, а затем закрываются все подключения.
*/