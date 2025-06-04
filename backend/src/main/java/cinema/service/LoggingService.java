package cinema.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LoggingService {

    private final String file = "log.txt";

    public void log(String action) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("[" + LocalDateTime.now() + "] " + action + "\n");
        } catch (IOException e) {
            System.err.println("Nie można zapisać logu: " + e.getMessage());
        }
    }
}
