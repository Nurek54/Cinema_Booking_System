package cinema.model;

import java.time.LocalDateTime;

public class Screening {
    private Long id;
    private Movie movie;
    private Room room;
    private LocalDateTime dateTime;

    public Screening() {
    }

    public Screening(Long id, Movie movie, Room room, LocalDateTime dateTime) {
        this.id = id;
        this.movie = movie;
        this.room = room;
        this.dateTime = dateTime;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
