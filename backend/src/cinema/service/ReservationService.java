package cinema.service;

import cinema.model.Reservation;
import cinema.model.Screening;
import cinema.repository.ReservationRepository;
import cinema.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final LoggingService loggingService;

    public ReservationService(ReservationRepository reservationRepository,
                              ScreeningRepository screeningRepository,
                              LoggingService loggingService) {
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
        this.loggingService = loggingService;
    }

    public Reservation createReservation(Long screeningId, List<Integer> seats) {
        if (seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Nie wybrano żadnych miejsc.");
        }

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Seans o ID=" + screeningId + " nie istnieje."));

        int totalSeats = screening.getRoom().getSeats();

        // walidacja numerów miejsc
        for (int seat : seats) {
            if (seat < 1 || seat > totalSeats) {
                throw new IllegalArgumentException(
                        "Miejsce " + seat + " nie istnieje (sala ma " + totalSeats + " miejsc).");
            }
        }

        // sprawdzenie duplikatów w żądaniu
        Set<Integer> uniqueSeats = Set.copyOf(seats);
        if (uniqueSeats.size() != seats.size()) {
            throw new IllegalArgumentException("Lista miejsc zawiera duplikaty.");
        }

        // sprawdzenie dostępności
        Set<Integer> takenSeats = getTakenSeats(screeningId);
        List<Integer> conflicts = seats.stream()
                .filter(takenSeats::contains)
                .collect(Collectors.toList());

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException(
                    "Miejsca już zajęte: " + conflicts);
        }

        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setSeats(new ArrayList<>(seats));

        Reservation saved = reservationRepository.save(reservation);
        loggingService.log("Rezerwacja #" + saved.getId() + " na seans #" + screeningId
                + ", miejsca: " + seats);
        return saved;
    }

    public List<Integer> getAvailableSeats(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Seans o ID=" + screeningId + " nie istnieje."));

        int totalSeats = screening.getRoom().getSeats();
        Set<Integer> takenSeats = getTakenSeats(screeningId);

        return IntStream.rangeClosed(1, totalSeats)
                .filter(n -> !takenSeats.contains(n))
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsByScreeningId(Long screeningId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getScreening().getId().equals(screeningId))
                .collect(Collectors.toList());
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public boolean deleteById(Long id) {
        boolean deleted = reservationRepository.deleteById(id);
        if (deleted) {
            loggingService.log("Usunięto rezerwację ID=" + id);
        }
        return deleted;
    }

    // ---------- helper ----------

    private Set<Integer> getTakenSeats(Long screeningId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getScreening().getId().equals(screeningId))
                .flatMap(r -> r.getSeats().stream())
                .collect(Collectors.toSet());
    }
}