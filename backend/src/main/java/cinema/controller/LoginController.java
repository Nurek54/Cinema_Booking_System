package cinema.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    // login: "admin", hasło: "admin" → hash SHA-256 poniżej
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS_HASH =
            "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Brak danych");
        }

        String hashed = sha256(password);
        if (ADMIN_USER.equals(username) && ADMIN_PASS_HASH.equals(hashed)) {
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/logs")
    public ResponseEntity<String> getLogs() throws IOException {
        File logFile = new File("log.txt");
        if (!logFile.exists()) {
            return ResponseEntity.ok("Brak logów.");
        }
        String content = Files.readString(logFile.toPath());
        return ResponseEntity.ok(content);
    }

    // Pomocnicza metoda do SHA-256
    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
