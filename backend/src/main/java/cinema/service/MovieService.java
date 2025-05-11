package cinema.service;

import cinema.model.Movie;
import cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) { this.repo = repo; }

    public Movie addMovie(Movie m)                  { return repo.save(m); }
    public List<Movie> getAllMovies()               { return repo.findAll(); }
    public Optional<Movie> getMovieById(Long id)    { return repo.findById(id); }

    /** zmienione – wywołuje repo.deleteById */
    public boolean deleteMovie(Long id)             { return repo.deleteById(id); }

    public Optional<Movie> updateMovie(Long id, Movie upd) {
        return repo.findById(id).map(m -> {
            m.setTitle(upd.getTitle());
            m.setDurationMinutes(upd.getDurationMinutes());
            return m;
        });
    }
}
