package cinema.model;

import java.util.List;

public class Reservation {
    private Long id;
    private Screening screening;
    private List<Integer> seats;

    public Reservation() {
    }

    public Reservation(Long id, Screening screening, List<Integer> seats) {
        this.id = id;
        this.screening = screening;
        this.seats = seats;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
}
