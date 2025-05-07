
# 📄 Document Backend (Spring Boot)

Backend-сервис для загрузки и хранения нормативной документации нефтегазового сектора.

## 📦 Функционал

- Загрузка документа (PDF, DOCX, TXT)
- Извлечение текста из документа (Apache Tika)
- Сохранение информации в базу PostgreSQL
- Получение списка всех документов
- Получение документа по ID

## 🚀 Технологии

- Java 21 + Spring Boot
- PostgreSQL
- Apache Tika
- REST API (Postman)

## 📚 API

| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/documents/upload` | Загрузить документ |
| GET  | `/api/documents` | Получить все документы |
| GET  | `/api/documents/{id}` | Получить документ по ID |

## ▶️ Запуск проекта

### 1️⃣ Установить что потребуется

- Java 21 или 17
- PostgreSQL (создать базу данных)
- IntelliJ IDEA

### 2️⃣ Настроить базу в `application.properties`

```
spring.datasource.url=jdbc:postgresql://localhost:5432/document_db
spring.datasource.username=postgres
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Запустить проект

- Открыть в IntelliJ IDEA
- Найти класс `Application.java`
- Нажать "Run" → сервер стартует на порту 8080

### 4️⃣ Использовать готовую коллекцию в Postman

- Импортировать файл `document-api.postman_collection.json` из репозитория
- Выполнить следующие запросы:

    - POST → Загрузка документа
    - GET → Получение всех документов
    - GET → Получение документа по ID

### 5️⃣ Проверить работу

- При загрузке → получить ID
- Проверить список документов
- Проверить получение по ID

---

## 📌 Дополнительно

Можно расширить проект:
- Добавить Elasticsearch для поиска
- Добавить Swagger для документации

## 👨‍💻 Автор проекта

- Дарья (AnuchinaDaria)

---

### 📢 ВАЖНО (для Бори)

Просто следуй инструкциям выше (настрой БД, запусти проект и используй Postman) → всё готово для тестирования и доработки.

