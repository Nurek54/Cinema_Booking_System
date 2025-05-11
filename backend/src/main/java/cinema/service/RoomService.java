package cinema.service;

import cinema.model.Room;
import cinema.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) { this.roomRepository = roomRepository; }

    public List<Room> getAllRooms()                   { return roomRepository.findAll(); }
    public Optional<Room> getRoomById(Long id)        { return roomRepository.findById(id); }
    public Room addRoom(Room room)                    { return roomRepository.save(room); }
}
