package cinema.repository;

import cinema.model.Movie;
import cinema.model.Room;
import cinema.model.Screening;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepo;
    private final MovieRepository movieRepo;
    private final ScreeningRepository screeningRepo;

    public DataSeeder(RoomRepository roomRepo,
                      MovieRepository movieRepo,
                      ScreeningRepository screeningRepo) {
        this.roomRepo = roomRepo;
        this.movieRepo = movieRepo;
        this.screeningRepo = screeningRepo;
    }

    @Override
    public void run(String... args) {
        if (roomRepo.findAll().isEmpty()) {
            roomRepo.save(new Room(null, "Sala 1", 60));
            roomRepo.save(new Room(null, "VIP", 30));
            System.out.println("[SEED] Załadowano sale.");
        }

        if (movieRepo.findAll().isEmpty()) {
            addMovie("Oppenheimer", 180);
            addMovie("Dune: Part Two", 166);
            addMovie("Interstellar", 169);
            addMovie("The Batman", 176);
            addMovie("Spider-Man: Across the Spider-Verse", 140);
            addMovie("Gladiator II", 148);
            addMovie("Inception", 148);
            addMovie("Joker: Folie à Deux", 138);
            System.out.println("[SEED] Załadowano filmy.");
        }

        if (screeningRepo.findAll().isEmpty()) {
            seedScreenings();
            System.out.println("[SEED] Załadowano seanse.");
        }
    }

    private void seedScreenings() {
        Room sala1 = roomRepo.findAll().get(0);
        Room vip   = roomRepo.findAll().get(1);

        LocalDate today = LocalDate.now();
        LocalDate d1 = today;
        LocalDate d2 = today.plusDays(1);
        LocalDate d3 = today.plusDays(2);
        LocalDate d4 = today.plusDays(3);
        LocalDate d5 = today.plusDays(4);

        var m = movieRepo.findAll();
        // 0=Oppenheimer  1=Dune  2=Interstellar  3=Batman
        // 4=Spider-Verse  5=Gladiator  6=Inception  7=Joker

        // ── Dziś ──
        screen(m.get(0), sala1, d1, 14, 0);
        screen(m.get(1), vip,   d1, 15, 0);
        screen(m.get(2), sala1, d1, 18, 0);
        screen(m.get(6), vip,   d1, 18, 30);
        screen(m.get(3), sala1, d1, 21, 30);
        screen(m.get(7), vip,   d1, 21, 30);

        // ── Jutro ──
        screen(m.get(4), sala1, d2, 12, 0);
        screen(m.get(5), vip,   d2, 13, 0);
        screen(m.get(0), sala1, d2, 15, 0);
        screen(m.get(2), vip,   d2, 16, 0);
        screen(m.get(1), sala1, d2, 19, 0);
        screen(m.get(3), vip,   d2, 19, 0);
        screen(m.get(6), sala1, d2, 22, 0);
        screen(m.get(7), vip,   d2, 22, 0);

        // ── Za 2 dni ──
        screen(m.get(1), sala1, d3, 14, 0);
        screen(m.get(4), vip,   d3, 14, 30);
        screen(m.get(5), sala1, d3, 17, 30);
        screen(m.get(0), vip,   d3, 17, 30);
        screen(m.get(2), sala1, d3, 21, 0);
        screen(m.get(7), vip,   d3, 20, 30);

        // ── Za 3 dni ──
        screen(m.get(3), sala1, d4, 13, 0);
        screen(m.get(6), vip,   d4, 14, 0);
        screen(m.get(0), sala1, d4, 17, 0);
        screen(m.get(5), vip,   d4, 17, 0);
        screen(m.get(4), sala1, d4, 21, 0);
        screen(m.get(1), vip,   d4, 20, 0);

        // ── Za 4 dni ──
        screen(m.get(2), sala1, d5, 15, 0);
        screen(m.get(7), sala1, d5, 19, 0);
        screen(m.get(6), vip,   d5, 15, 30);
        screen(m.get(3), vip,   d5, 18, 30);
    }

    private void addMovie(String title, int duration) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setDurationMinutes(duration);
        movieRepo.save(m);
    }

    private void screen(Movie movie, Room room, LocalDate date, int hour, int minute) {
        Screening s = new Screening();
        s.setMovie(movie);
        s.setRoom(room);
        s.setDateTime(LocalDateTime.of(date, LocalTime.of(hour, minute)));
        screeningRepo.save(s);
    }
}