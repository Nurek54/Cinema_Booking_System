package cinema.repository;

import cinema.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository {

    private final List<Movie> movies = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("movies.json");
    private long nextId = 1;

    public MovieRepository() {
        load();
        movies.stream().mapToLong(Movie::getId).max().ifPresent(max -> nextId = max + 1);
    }

    // ---------- API ----------
    public List<Movie> findAll()            { return new ArrayList<>(movies); }
    public Optional<Movie> findById(Long id){ return movies.stream().filter(m -> m.getId().equals(id)).findFirst(); }

    public Movie save(Movie movie) {
        movie.setId(nextId++);
        movies.add(movie);
        saveToFile();
        return movie;
    }

    /** NOWE – usuwanie + zapisywanie na dysk */
    public boolean deleteById(Long id) {
        boolean removed = movies.removeIf(m -> m.getId().equals(id));
        if (removed) saveToFile();
        return removed;
    }

    // ---------- I/O ----------
    private void load() {
        if (!file.exists()) return;
        try {
            movies.addAll(mapper.readValue(file, new TypeReference<>() {}));
        } catch (IOException e) {
            System.err.println("Błąd wczytywania filmów: " + e.getMessage());
        }
    }
    private void saveToFile() {
        try { mapper.writerWithDefaultPrettyPrinter().writeValue(file, movies); }
        catch (IOException e) { System.err.println("Błąd zapisu filmów: " + e.getMessage()); }
    }
}
