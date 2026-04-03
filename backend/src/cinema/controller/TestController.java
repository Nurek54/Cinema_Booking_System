package cinema.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @CrossOrigin(origins = "http://localhost:5179")
    @GetMapping("/api/test") // Explicitna ścieżka
    public String test() {
        System.out.println("Endpoint /api/test został wywołany!");
        return "Backend działa poprawnie! " + System.currentTimeMillis();
    }
}