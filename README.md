# 🎮 Playtime Counter

A RESTful backend API built with **Spring Boot** and **MongoDB** for tracking your video game library — including playtime, platform, genre, and personal notes.

---

## Tech Stack

- **Java** (Spring Boot 2.7)
- **MongoDB** (via Spring Data MongoDB)
- **Maven**

---

## Project Structure

```
src/main/java/net/testspring/myApp/
├── PlaytimeCounterApplication.java   # App entry point
├── controller/
│   ├── GameEntryController.java      # Main REST controller (/games)
│   ├── GameEntryControllerV1.java    # Legacy in-memory controller (/_games)
│   └── HealthCheck.java             # Health check endpoint
├── entity/
│   └── GameEntry.java               # Game data model
├── repository/
│   └── GameEntryRepository.java     # MongoDB repository
└── service/
    └── GameEntryService.java        # Business logic layer
```

---

## Getting Started

### Prerequisites

- Java 8+
- Maven
- MongoDB running locally on port `27017`

### Run the App

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## API Reference

### Base URL: `/games` (MongoDB-backed)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/games/getall` | Get all game entries |
| `GET` | `/games/id/{id}` | Get a game entry by ID |
| `POST` | `/games` | Add a new game entry |
| `PUT` | `/games/id/{id}` | Update an existing game entry |
| `DELETE` | `/games/id/{id}` | Delete a game entry |

### Base URL: `/_games` (In-memory, no persistence)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/_games/getall` | List all in-memory entries |
| `POST` | `/_games` | Add an in-memory entry |

### Health Check

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/healthcheck` | Returns `OK` if server is up |

---

## Example Request

**POST** `/games`

```json
{
  "title":    "Elden Ring",
  "playtime": "120h 45m",
  "platform": "PC",
  "genre":    "Action RPG",
  "notes":    "Finished all bosses. Incredible game."
}
```

**Response** `201 Created`

```json
{
  "id": "64f1a2b3c4d5e6f7a8b9c0d1",
  "title": "Elden Ring",
  "playtime": "120h 45m",
  "platform": "PC",
  "genre": "Action RPG",
  "notes": "Finished all bosses. Incredible game.",
  "date": "2026-08-01T18:30:00"
}
```

---

## Game Entry Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | ObjectId | Auto-generated MongoDB ID |
| `title` | String | Name of the game |
| `playtime` | String | Time played (e.g. `"42h 30m"`) |
| `platform` | String | Platform (PC, PS5, Xbox, Switch…) |
| `genre` | String | Game genre (RPG, FPS, Adventure…) |
| `notes` | String | Personal notes about the game |
| `date` | LocalDateTime | Auto-set on create and update |

---

## Configuration

Edit `src/main/resources/application.properties` to change the MongoDB connection:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=playtimedb
```

---

## Partial Updates

`PUT /games/id/{id}` supports partial updates — only fields included in the request body are updated. Fields left out remain unchanged.
