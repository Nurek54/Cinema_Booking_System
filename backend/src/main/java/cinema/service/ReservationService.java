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

    public ReservationService(ReservationRepository reservationRepository,
                              ScreeningService screeningService) {
        this.reservationRepository = reservationRepository;
        this.screeningService      = screeningService;
    }

    /* ------------------------------------------------- CREATE */
    public Reservation createReservation(Long screeningId, List<Integer> seatNumbers) {

        Screening screening = screeningService.getById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Seans nie istnieje."));

        // 1) sanity-check listy (brak duplikatów)
        Set<Integer> duplicateCheck = new HashSet<>();
        for (Integer s : seatNumbers) {
            if (!duplicateCheck.add(s))
                throw new IllegalArgumentException("Duplikat miejsca: " + s);
        }

        // 2) zakres i zajętość
        List<Integer> takenSeats = getTakenSeats(screeningId);
        for (Integer seat : seatNumbers) {
            if (seat < 1 || seat > screening.getRoom().getSeats())
                throw new IllegalArgumentException("Nieprawidłowy numer miejsca: " + seat);
            if (takenSeats.contains(seat))
                throw new IllegalStateException("Miejsce " + seat + " jest już zajęte.");
        }

        // 3) zapis (posortowana lista dla porządku w pliku)
        List<Integer> sorted = seatNumbers.stream().sorted().toList();
        Reservation res = new Reservation(null, screening, sorted);
        return reservationRepository.save(res);
    }

    /* ------------------------------------------------- READ helpers */
    public List<Integer> getTakenSeats(Long screeningId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getScreening().getId().equals(screeningId))
                .flatMap(r -> r.getSeats().stream())
                .toList();
    }

    public List<Integer> getAvailableSeats(Long screeningId) {
        Screening s = screeningService.getById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Seans nie istnieje."));
        int total = s.getRoom().getSeats();
        Set<Integer> taken = new HashSet<>(getTakenSeats(screeningId));

        return java.util.stream.IntStream.rangeClosed(1, total)
                .filter(n -> !taken.contains(n))
                .boxed()
                .toList();
    }

    public List<Reservation> getReservationsByScreeningId(Long id) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getScreening().getId().equals(id))
                .toList();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /* ------------------------------------------------- DELETE */
    public boolean deleteReservation(Long id) {
        return reservationRepository.deleteById(id);
    }
}
