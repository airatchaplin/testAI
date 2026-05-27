# testAI — REST API управления товарами

REST API сервис для управления каталогом товаров, разработанный на **Spring Boot** с использованием **PostgreSQL** и **Liquibase**.

## 🎯 Цель проекта

Предоставить простой и надёжный REST API для CRUD-операций с товарами.  
Проект демонстрирует многослойную архитектуру (Controller → Service → Repository), чисто́ту кода, принципы REST и best practices Spring Boot.

## 🧰 Стек технологий

| Технология | Версия | Назначение |
|---|---|---|
| Java | 22 | Язык программирования |
| Spring Boot | 3.4.4 | Фреймворк приложения |
| Spring Data JPA / Hibernate | — | Доступ к данным, ORM |
| PostgreSQL | — | Основная база данных |
| Liquibase | — | Миграции схемы БД |
| Maven | — | Сборка и управление зависимостями |
| SpringDoc OpenAPI | 2.8.6 | Документация API (Swagger) |
| H2 | — | In-memory БД для тестов |
| JUnit 5 / Mockito | — | Модульное тестирование |

## 🏗️ Архитектура

```
┌─────────────────────────────────────────────┐
│             ProductController                │  ← REST endpoints
├─────────────────────────────────────────────┤
│              ProductService                  │  ← Бизнес-логика
├─────────────────────────────────────────────┤
│          ProductRepository (JPA)             │  ← Доступ к данным
├─────────────────────────────────────────────┤
│              PostgreSQL / H2                 │  ← База данных
└─────────────────────────────────────────────┘
```

### Пакеты проекта

| Пакет | Назначение |
|---|---|
| `com.testai` | Главный класс приложения |
| `com.testai.controller` | REST-контроллеры |
| `com.testai.controller.advice` | Глобальный обработчик исключений |
| `com.testai.dto` | DTO (Data Transfer Objects) для запросов/ответов |
| `com.testai.entity` | JPA-сущности |
| `com.testai.exception` | Кастомные исключения |
| `com.testai.mapper` | Мапперы Entity ↔ DTO |
| `com.testai.repository` | Spring Data JPA репозитории |
| `com.testai.service` | Сервисный слой с бизнес-логикой |

## 📋 API Endpoints

Все эндпоинты находятся на базовом пути `/api/products`.

| Метод | URL | Описание | Статусы ответа |
|---|---|---|---|
| `GET` | `/api/products` | Получить список всех товаров | `200 OK` |
| `GET` | `/api/products/{id}` | Получить товар по ID | `200 OK`, `404 Not Found` |
| `POST` | `/api/products` | Создать новый товар | `201 Created`, `400 Bad Request` |
| `PUT` | `/api/products/{id}` | Обновить существующий товар | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `DELETE` | `/api/products/{id}` | Удалить товар | `204 No Content`, `404 Not Found` |

### Пример запроса (создание товара)

**Request:**
```http
POST /api/products
Content-Type: application/json

{
  "name": "Смартфон",
  "description": "Современный смартфон с отличной камерой",
  "price": 59999.99
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Смартфон",
  "description": "Современный смартфон с отличной камерой",
  "price": 59999.99
}
```

### Формат ошибок

При ошибках API возвращает единообразную структуру:

```json
{
  "timestamp": "2026-05-27T12:00:00",
  "status": 404,
  "error": "Product not found with id: 99"
}
```

Для ошибок валидации (`400 Bad Request`):

```json
{
  "timestamp": "2026-05-27T12:00:00",
  "status": 400,
  "errors": {
    "name": "Name is required",
    "price": "Price must be positive or zero"
  }
}
```

## 🔧 Предварительные требования

- **Java 22+** (установить [OpenJDK](https://jdk.java.net/22/) или [Oracle JDK](https://www.oracle.com/java/technologies/downloads/))
- **Apache Maven 3.9+**
- **PostgreSQL 14+**
- **Git**

## 🚀 Локальный запуск

### 1. Клонировать репозиторий

```bash
git clone https://github.com/<username>/testAI.git
cd testAI
```

### 2. Настроить базу данных

Создайте базу данных в PostgreSQL:

```sql
CREATE DATABASE testai;
```

Учётные данные по умолчанию (из `application.yml`):
- **URL:** `jdbc:postgresql://localhost:5432/testai`
- **Username:** `postgres`
- **Password:** `postgres`

При необходимости измените настройки в `src/main/resources/application.yml`.

### 3. Собрать и запустить

```bash
# Сборка (без тестов)
mvn clean package -DskipTests

# Запуск с профилем dev
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Или запуск собранного JAR
java -jar target/testai-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### 4. Проверить работу

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
- Health check: `curl http://localhost:8080/api/products`

## 📂 Профили конфигурации

### `dev` (разработка)

```yaml
spring:
  jpa:
    show-sql: true
logging:
  level:
    com.testai: DEBUG
    org.springframework.web: DEBUG
```

- SQL-запросы выводятся в консоль
- Подробное логирование (`DEBUG`) для пакета `com.testai`

### `prod` (продакшн)

```yaml
spring:
  jpa:
    show-sql: false
logging:
  level:
    com.testai: WARN
```

- SQL-запросы скрыты
- Логирование только предупреждений и ошибок (`WARN`)

## 🧪 Тестирование

```bash
# Запуск всех тестов
mvn test

# Запуск конкретного теста
mvn test -Dtest=ProductServiceTest
```

В тестах используется H2 in-memory база данных (Liquibase отключён, `ddl-auto: create-drop`).

## 🗄️ Миграции БД (Liquibase)

Миграции находятся в `src/main/resources/db/changelog/`:

```
db/changelog/
├── db.changelog-master.yaml          # Главный файл
└── changes/
    └── 001-create-products-table.yaml  # Создание таблицы products
```

При запуске приложения Liquibase автоматически применяет миграции к PostgreSQL.

## 🔗 Полезные ссылки

| Ресурс | URL |
|---|---|
| Swagger UI | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| OpenAPI JSON | [http://localhost:8080/api-docs](http://localhost:8080/api-docs) |
| Статическая OpenAPI-спецификация | [`openapi.yaml`](src/main/resources/static/openapi.yaml) |

## 📄 Лицензия

Проект распространяется без лицензии — учебный/демонстрационный код.
