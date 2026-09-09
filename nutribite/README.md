# NutriBite Backend

Spring Boot 3.5.4 / Java 21 backend for NutriBiteAI.

## Run locally

1. Create a PostgreSQL database named `NutriBite`.
2. Copy `.env.example` values into your IDE/terminal environment. Do not commit real database passwords or JWT secrets.
3. Start with `mvnw.cmd spring-boot:run` on Windows or `./mvnw spring-boot:run` on Linux/macOS.
4. Open Swagger at `http://localhost:8080/swagger-ui.html`.

Development mode uses Hibernate `ddl-auto=update`, so the application creates/updates the tables represented by the JPA entities. The bundled Indian food CSV is imported automatically on first startup when the `foods` table is empty.

## Core API

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/profile`
- `PUT /api/profile`
- `POST /api/onboarding`
- `GET /api/profile/health`
- `GET /api/nutrition`
- `GET /api/foods`
- `GET /api/foods/{id}`
- `GET /api/foods/search?query=dosa`
- `GET /api/recipes`
- `GET /api/recipes/{id}`
- `POST /api/recipes/{id}/favorite`
- `POST /api/water`
- `GET /api/water/today`
- `GET /api/water/summary`
- `POST /api/weight`
- `GET /api/weight/current`
- `GET /api/weight/history`
- `GET /api/goals`
- `PUT /api/goals`
- `GET /api/dashboard`
- `POST /api/snap/analyze`

## Nutrition contract

`GET /api/nutrition` returns backend-calculated values using the user's profile:

```json
{
  "calories": 1518.6,
  "protein": 104.0,
  "carbs": 151.8,
  "fat": 42.2,
  "fibre": 21.3
}
```

The UI should display these values; nutrition calculations stay on the backend.

## Notes

The Snap AI endpoint intentionally does not fabricate recognition results. It returns a clear `501 Not Implemented` response until a real image-analysis provider is wired in.
