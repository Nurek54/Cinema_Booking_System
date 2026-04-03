package cinema.service;

import cinema.model.Movie;
import cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final LoggingService loggingService;

    public MovieService(MovieRepository movieRepository, LoggingService loggingService) {
        this.movieRepository = movieRepository;
        this.loggingService = loggingService;
    }

    public Movie addMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new IllegalArgumentException("Tytuł filmu nie może być pusty.");
        }
        if (movie.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Czas trwania musi być większy od 0.");
        }
        Movie saved = movieRepository.save(movie);
        loggingService.log("Dodano film: " + saved.getTitle() + " (ID=" + saved.getId() + ")");
        return saved;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public boolean deleteMovie(Long id) {
        boolean deleted = movieRepository.deleteById(id);
        if (deleted) {
            loggingService.log("Usunięto film ID=" + id);
        }
        return deleted;
    }

    public Optional<Movie> updateMovie(Long id, Movie updatedMovie) {
        Optional<Movie> existing = movieRepository.findById(id);
        if (existing.isEmpty()) return Optional.empty();

        Movie movie = existing.get();
        if (updatedMovie.getTitle() != null && !updatedMovie.getTitle().isBlank()) {
            movie.setTitle(updatedMovie.getTitle());
        }
        if (updatedMovie.getDurationMinutes() > 0) {
            movie.setDurationMinutes(updatedMovie.getDurationMinutes());
        }
        movieRepository.flush();
        loggingService.log("Zaktualizowano film ID=" + id + ": " + movie.getTitle());
        return Optional.of(movie);
    }
}