<p align="center">
  <br />
  <strong>◈</strong>
  <br />
  <br />
</p>

<h1 align="center">LUMIÈRE CINEMA</h1>

<p align="center">
  <em>A full-stack cinema booking system with a premium dark UI</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?style=flat-square" alt="Java" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-green?style=flat-square" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Vite-5.x-purple?style=flat-square" alt="Vite" />
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="License" />
</p>

<br />

<p align="center">
  <img src="preview.png" alt="Lumière Cinema — Hero" width="720" />
</p>

---

## About

**Lumière Cinema** is a cinema seat reservation system built as a full-stack web application. It features a Spring Boot REST API backend with JSON file-based persistence and a vanilla JavaScript frontend served via Vite, wrapped in a luxury dark cinematic theme with gold accents, film‑strip decorations and smooth animations.

The project name is inspired by the **Lumière brothers** — pioneers of cinema — and the visual aesthetic draws inspiration from the game **[Expedition 33](https://expedition33.com/)**, blending cinematic atmosphere with modern UI design.

## User Flow

```
Repertoire  →  Pick a movie  →  See screenings  →  Select seats  →  Booking confirmation
```

The customer never sees admin forms — only the movie grid, screening list and seat picker. All management happens behind a separate login.

## Features

- **Cinematic landing page** — full‑screen hero with animated spotlights, film‑strip decorations, live stats and a scroll indicator
- **Movie poster grid** — cards with unique gradient backgrounds, golden glow on hover and staggered entrance animations
- **Screening browser** — pick a movie, see all upcoming showtimes sorted by date with room info
- **Interactive seat reservation** — visual seat grid with screen glow, click‑to‑select, real‑time availability and booking summary
- **Booking confirmation** — receipt card with reservation number, film, room, date and seats
- **Admin panel** (login required) — four tabs: Movies CRUD, Screenings CRUD, Reservations management, System logs
- **Room conflict detection** — the backend prevents overlapping screenings (film duration + 15 min buffer)
- **Seat validation** — checks for duplicates, out‑of‑range seats and double‑bookings
- **Persistent storage** — all data saved to JSON files (`movies.json`, `screenings.json`, `reservations.json`)
- **Responsive design** — works on desktop and mobile with hamburger navigation
- **Toast notifications** — non‑intrusive feedback for all user actions

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17+, Spring Boot 3.x |
| Frontend | HTML5, CSS3, Vanilla JavaScript (ES Modules) |
| Build Tool | Vite 5.x (dev server + API proxy) |
| Persistence | JSON files via Jackson |
| Fonts | Playfair Display, DM Sans, JetBrains Mono (Google Fonts) |

## Project Structure

```
Cinema_Booking_System/
├── backend/
│   ├── pom.xml
│   ├── mvnw.cmd / mvnw              # Maven Wrapper (no install needed)
│   └── src/
│       ├── cinema/
│       │   ├── CinemaApplication.java
│       │   ├── CorsConfig.java
│       │   ├── controller/
│       │   │   ├── MovieController.java
│       │   │   ├── ScreeningController.java
│       │   │   ├── ReservationController.java
│       │   │   ├── RoomController.java
│       │   │   └── LoginController.java
│       │   ├── model/
│       │   │   ├── Movie.java
│       │   │   ├── Room.java
│       │   │   ├── Screening.java
│       │   │   └── Reservation.java
│       │   ├── repository/
│       │   │   ├── MovieRepository.java
│       │   │   ├── ScreeningRepository.java
│       │   │   ├── ReservationRepository.java
│       │   │   ├── RoomRepository.java
│       │   │   ├── RepositoryConfig.java
│       │   │   └── DataSeeder.java
│       │   └── service/
│       │       ├── MovieService.java
│       │       ├── ScreeningService.java
│       │       ├── ReservationService.java
│       │       ├── RoomService.java
│       │       └── LoggingService.java
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── index.html
│   ├── vite.config.js
│   └── src/
│       ├── css/styles.css
│       └── js/app.js
└── README.md
```

## Getting Started

### Prerequisites

- **Java 17** or newer
- **Node.js** 18+ and npm
- Maven is **not** required — the project includes Maven Wrapper

### Backend

```bash
cd backend
.\mvnw.cmd spring-boot:run      # Windows
./mvnw spring-boot:run           # Linux / macOS
```

The API starts at `http://localhost:8080`. On first run, the `DataSeeder` automatically populates **2 rooms**, **8 movies** and **30 screenings** spread across the next 5 days.

> If you need a fresh database, delete `movies.json`, `screenings.json` and `reservations.json` from the backend directory before starting.

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Opens at `http://localhost:5178` with API requests proxied to the backend.

## API Endpoints

### Movies
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/movies` | List all movies |
| `GET` | `/api/movies/:id` | Get movie by ID |
| `POST` | `/api/movies` | Add a new movie |
| `PUT` | `/api/movies/:id` | Update a movie |
| `DELETE` | `/api/movies/:id` | Delete a movie |

### Screenings
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/screenings` | List all screenings |
| `GET` | `/api/screenings/:id` | Get screening by ID |
| `GET` | `/api/screenings/movie/:movieId` | Screenings for a specific movie |
| `POST` | `/api/screenings` | Create a screening |
| `DELETE` | `/api/screenings/:id` | Delete a screening |
| `GET` | `/api/screenings/:id/availability` | Get available seat numbers |

### Reservations
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/reservations` | List all reservations |
| `GET` | `/api/reservations/:screeningId` | Reservations for a screening |
| `POST` | `/api/reservations/:screeningId` | Book seats (body: `[1, 2, 3]`) |
| `DELETE` | `/api/reservations/:id` | Cancel a reservation |

### Admin
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/login` | Admin login (returns `"OK"`) |
| `GET` | `/api/logs` | Get system log file |

## Default Credentials

| Username | Password |
|----------|----------|
| `admin` | `admin` |

> Authentication uses client‑side SHA‑256 hash verification. This is a demo project — do not use in production.

## Seeded Data

On first launch the application creates:

| Entity | Count | Details |
|--------|-------|---------|
| Rooms | 2 | Sala 1 (60 seats), VIP (30 seats) |
| Movies | 8 | Oppenheimer, Dune: Part Two, Interstellar, The Batman, Spider‑Man: Across the Spider‑Verse, Gladiator II, Inception, Joker: Folie à Deux |
| Screenings | 30 | 3–4 per movie, spread across 5 days in both rooms |

## License

This project is available under the [MIT License](LICENSE).