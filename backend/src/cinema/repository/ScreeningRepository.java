package cinema.repository;

import cinema.model.Screening;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScreeningRepository {

    private final List<Screening> screenings = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final File file = new File("screenings.json");
    private long nextId = 1;

    public ScreeningRepository() {
        load();
        screenings.stream().mapToLong(Screening::getId).max().ifPresent(max -> nextId = max + 1);
    }

    // -------------------- API --------------------

    public List<Screening> findAll() {
        return new ArrayList<>(screenings);
    }

    public Optional<Screening> findById(Long id) {
        return screenings.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Screening save(Screening s) {
        s.setId(nextId++);
        screenings.add(s);
        saveToFile();
        return s;
    }

    public boolean deleteById(Long id) {
        boolean removed = screenings.removeIf(s -> s.getId().equals(id));
        if (removed) saveToFile();
        return removed;
    }

    // -------------------- I/O --------------------

    private void load() {
        if (!file.exists()) return;
        try {
            List<Screening> loaded = mapper.readValue(file, new TypeReference<List<Screening>>() {});
            if (loaded != null) screenings.addAll(loaded);
        } catch (IOException e) {
            System.err.println("screenings.json uszkodzony → " + e.getMessage());
            File bad = new File("screenings_corrupt_" + System.currentTimeMillis() + ".json");
            if (file.renameTo(bad)) System.err.println("Kopia zapasowa: " + bad.getName());
        }
    }

    private void saveToFile() {
        try {
            File tmp = new File(file.getPath() + ".tmp");
            mapper.writerWithDefaultPrettyPrinter().writeValue(tmp, screenings);
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Błąd zapisu seansów: " + e.getMessage());
        }
    }
}