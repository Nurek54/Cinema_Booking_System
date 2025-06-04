package cinema.controller;

import cinema.model.Reservation;
import cinema.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{screeningId}")
    public ResponseEntity<?> createReservation(@PathVariable Long screeningId,
                                               @RequestBody List<Integer> seats) {
        try {
            Reservation saved = reservationService.createReservation(screeningId, seats);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{screeningId}/available")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable Long screeningId) {
        return ResponseEntity.ok(reservationService.getAvailableSeats(screeningId));
    }

    @GetMapping("/{screeningId}")
    public ResponseEntity<List<Reservation>> getByScreening(@PathVariable Long screeningId) {
        return ResponseEntity.ok(reservationService.getReservationsByScreeningId(screeningId));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
