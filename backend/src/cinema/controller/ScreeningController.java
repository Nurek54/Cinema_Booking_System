package cinema.controller;

import cinema.model.Screening;
import cinema.service.ReservationService;
import cinema.service.ScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;
    private final ReservationService reservationService;

    public ScreeningController(ScreeningService screeningService,
                               ReservationService reservationService) {
        this.screeningService   = screeningService;
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Screening screening) {
        try {
            return ResponseEntity.ok(screeningService.createScreening(screening));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAll() {
        return ResponseEntity.ok(screeningService.getAllScreenings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getById(@PathVariable Long id) {
        return screeningService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Seanse dla konkretnego filmu */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Screening>> getByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(screeningService.getByMovieId(movieId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return screeningService.deleteScreening(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<Integer>> availability(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getAvailableSeats(id));
    }
}