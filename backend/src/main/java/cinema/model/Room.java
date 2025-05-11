package cinema.model;

public class Room {
    private Long id;
    private String name;
    private int seats;

    public Room() {
    }

    public Room(Long id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
