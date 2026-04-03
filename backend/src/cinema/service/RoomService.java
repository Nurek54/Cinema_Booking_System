package cinema.service;

import cinema.model.Room;
import cinema.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final LoggingService loggingService;

    public RoomService(RoomRepository roomRepository, LoggingService loggingService) {
        this.roomRepository = roomRepository;
        this.loggingService = loggingService;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room addRoom(Room room) {
        if (room.getName() == null || room.getName().isBlank()) {
            throw new IllegalArgumentException("Nazwa sali nie może być pusta.");
        }
        if (room.getSeats() <= 0) {
            throw new IllegalArgumentException("Liczba miejsc musi być większa od 0.");
        }
        Room saved = roomRepository.save(room);
        loggingService.log("Dodano salę: " + saved.getName() + " (" + saved.getSeats() + " miejsc, ID=" + saved.getId() + ")");
        return saved;
    }
}