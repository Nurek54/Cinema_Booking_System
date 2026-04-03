package cinema.service;

import cinema.model.Movie;
import cinema.model.Room;
import cinema.model.Screening;
import cinema.repository.MovieRepository;
import cinema.repository.RoomRepository;
import cinema.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final LoggingService loggingService;

    public ScreeningService(ScreeningRepository screeningRepository,
                            MovieRepository movieRepository,
                            RoomRepository roomRepository,
                            LoggingService loggingService) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.loggingService = loggingService;
    }

    public Screening createScreening(Screening screening) {
        if (screening.getMovie() == null || screening.getMovie().getId() == null) {
            throw new IllegalArgumentException("Nie podano filmu.");
        }
        if (screening.getRoom() == null || screening.getRoom().getId() == null) {
            throw new IllegalArgumentException("Nie podano sali.");
        }
        if (screening.getDateTime() == null) {
            throw new IllegalArgumentException("Nie podano daty seansu.");
        }
        if (screening.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Nie można utworzyć seansu w przeszłości.");
        }

        Movie movie = movieRepository.findById(screening.getMovie().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Film o ID=" + screening.getMovie().getId() + " nie istnieje."));

        Room room = roomRepository.findById(screening.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sala o ID=" + screening.getRoom().getId() + " nie istnieje."));

        screening.setMovie(movie);
        screening.setRoom(room);

        LocalDateTime newStart = screening.getDateTime();
        LocalDateTime newEnd = newStart.plusMinutes(movie.getDurationMinutes() + 15);

        for (Screening existing : screeningRepository.findAll()) {
            if (!existing.getRoom().getId().equals(room.getId())) continue;

            LocalDateTime exStart = existing.getDateTime();
            LocalDateTime exEnd = exStart.plusMinutes(existing.getMovie().getDurationMinutes() + 15);

            if (newStart.isBefore(exEnd) && newEnd.isAfter(exStart)) {
                throw new IllegalStateException(
                        "Kolizja w sali \"" + room.getName() + "\": seans \"" +
                                existing.getMovie().getTitle() + "\" trwa od " +
                                exStart + " do ok. " + exEnd + ".");
            }
        }

        Screening saved = screeningRepository.save(screening);
        loggingService.log("Utworzono seans: \"" + movie.getTitle() + "\" w sali \"" +
                room.getName() + "\" na " + screening.getDateTime() + " (ID=" + saved.getId() + ")");
        return saved;
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    /** Seanse dla konkretnego filmu — posortowane wg daty */
    public List<Screening> getByMovieId(Long movieId) {
        return screeningRepository.findAll().stream()
                .filter(s -> s.getMovie().getId().equals(movieId))
                .filter(s -> s.getDateTime().isAfter(LocalDateTime.now().minusHours(1)))
                .sorted((a, b) -> a.getDateTime().compareTo(b.getDateTime()))
                .collect(Collectors.toList());
    }

    public Optional<Screening> getById(Long id) {
        return screeningRepository.findById(id);
    }

    public boolean deleteScreening(Long id) {
        boolean deleted = screeningRepository.deleteById(id);
        if (deleted) {
            loggingService.log("Usunięto seans ID=" + id);
        }
        return deleted;
    }
}