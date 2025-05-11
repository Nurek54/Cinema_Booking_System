package cinema.service;

import cinema.model.Movie;
import cinema.model.Room;
import cinema.model.Screening;
import cinema.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepo;
    private final MovieService movieService;
    private final RoomService roomService;

    public ScreeningService(ScreeningRepository screeningRepo,
                            MovieService movieService,
                            RoomService roomService) {
        this.screeningRepo = screeningRepo;
        this.movieService   = movieService;
        this.roomService    = roomService;
    }

    // ───────────────────────── CRUD ──────────────────────────

    public Screening createScreening(Screening draft) {
        // 1) pobierz pełne obiekty Movie i Room
        Movie movie = movieService.getMovieById(draft.getMovie().getId())
                .orElseThrow(() -> new IllegalArgumentException("Film nie istnieje"));
        Room  room  = roomService .getRoomById (draft.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Sala nie istnieje"));

        // 2) walidacja kolizji
        if (!isRoomAvailable(room, draft.getDateTime(), movie.getDurationMinutes())) {
            throw new IllegalStateException("Sala jest zajęta w tym przedziale czasowym.");
        }

        // 3) zapisz
        draft.setMovie(movie);
        draft.setRoom(room);
        return screeningRepo.save(draft);
    }

    public List<Screening> getAllScreenings()                    { return screeningRepo.findAll(); }

    public Optional<Screening> getById(Long id)                  {
        return screeningRepo.findAll().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public List<Screening> getScreeningsByMovieId(Long movieId)  {
        return screeningRepo.findAll().stream()
                .filter(s -> s.getMovie().getId().equals(movieId))
                .toList();
    }

    /** NOWE — kasowanie seansu */
    public boolean deleteScreening(Long id)                      { return screeningRepo.deleteById(id); }

    // ───────────────────────── Walidacja kolizji ──────────────
    private boolean isRoomAvailable(Room room, LocalDateTime start, int movieMinutes) {
        LocalDateTime end = start.plusMinutes(movieMinutes);

        return screeningRepo.findAll().stream()
                .filter(s -> s.getRoom().getId().equals(room.getId()))
                .noneMatch(existing -> {
                    LocalDateTime exStart = existing.getDateTime();
                    LocalDateTime exEnd   = exStart.plusMinutes(existing.getMovie().getDurationMinutes());
                    // kolizja: start < exEnd && exStart < end
                    return start.isBefore(exEnd) && exStart.isBefore(end);
                });
    }
}
