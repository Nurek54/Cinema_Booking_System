package cinema.service;

import cinema.model.Reservation;
import cinema.model.Screening;
import cinema.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;
    private final LoggingService loggingService;

    public ReservationService(ReservationRepository reservationRepository,
                              ScreeningService screeningService,
                              LoggingService loggingService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.loggingService = loggingService;
    }

    public Reservation createReservation(Long screeningId, List<Integer> seatNumbers) {
        Screening screening = screeningService.getById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Seans nie istnieje."));

        List<Integer> takenSeats = getTakenSeats(screeningId);
        for (Integer seat : seatNumbers) {
            if (seat < 1 || seat > screening.getRoom().getSeats()) {
                throw new IllegalArgumentException("Nieprawidłowy numer miejsca: " + seat);
            }
            if (takenSeats.contains(seat)) {
                throw new IllegalStateException("Miejsce " + seat + " jest już zajęte.");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setSeats(seatNumbers);

        Reservation saved = reservationRepository.save(reservation);
        loggingService.log("Dodano rezerwację ID: " + saved.getId() +
                ", Seans: " + screeningId + ", Miejsca: " + seatNumbers);
        return saved;
    }

    public List<Integer> getTakenSeats(Long screeningId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getScreening().getId().equals(screeningId))
                .flatMap(r -> r.getSeats().stream())
                .collect(Collectors.toList());
    }

    public List<Integer> getAvailableSeats(Long screeningId) {
        Screening screening = screeningService.getById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Seans nie istnieje."));

        int totalSeats = screening.getRoom().getSeats();
        Set<Integer> taken = new HashSet<>(getTakenSeats(screeningId));

        return new ArrayList<>(
                java.util.stream.IntStream.rangeClosed(1, totalSeats)
                        .filter(seat -> !taken.contains(seat))
                        .boxed()
                        .collect(Collectors.toList())
        );
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
        boolean ok = reservationRepository.deleteById(id);
        if (ok) {
            loggingService.log("Usunięto rezerwację ID: " + id);
        }
        return ok;
    }
}
