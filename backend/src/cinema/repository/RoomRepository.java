package cinema.repository;

import cinema.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();
    private long nextId = 1;

    public List<Room> findAll()            { return new ArrayList<>(rooms); }
    public Optional<Room> findById(Long id) { return rooms.stream().filter(r -> r.getId().equals(id)).findFirst(); }

    public Room save(Room room) {
        room.setId(nextId++);
        rooms.add(room);
        return room;
    }
}
