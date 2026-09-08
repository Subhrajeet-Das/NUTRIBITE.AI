# NutriBiteAI Frontend — 2026 rebuild

React + JavaScript + Vite frontend for the NutriBiteAI Spring Boot backend.

## Run

```powershell
npm.cmd install
npm.cmd run dev
```

Open `http://localhost:5173`.

## Backend contract used

Base URL comes from `VITE_API_BASE_URL` in `.env` and defaults to `http://localhost:8080/api`.

Authentication: `POST /auth/register`, `POST /auth/login`
Profile: `GET /profile`, `PUT /profile`, `GET /profile/health`
Nutrition: `GET /nutrition/recommendation`
Food library: `GET /foods/search?query=...`, `GET /foods/meal/{mealType}`, `GET /foods/diet/{dietType}`, `GET /foods/high-protein`, `GET /foods/low-calorie`

The frontend does not call meal-plan recommendation endpoints and does not fabricate a Snap AI response.

## Notes

- JWT is stored under one key: `nutribite_token`.
- Nutrition values are rendered from the backend response; the browser does not recreate the nutrition engine.
- Profile onboarding is persisted through `PUT /profile`.
- The supplied transparent NutriBiteAI logo is stored at `public/assets/nutribite-logo.png`.
- If PowerShell blocks `npm.ps1`, use `npm.cmd` as shown above.
