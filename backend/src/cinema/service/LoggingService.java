package cinema.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LoggingService {

    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String message) {
        String line = "[" + LocalDateTime.now().format(FMT) + "] " + message;
        System.out.println(line);
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.println(line);
        } catch (IOException e) {
            System.err.println("Błąd zapisu logu: " + e.getMessage());
        }
    }
}