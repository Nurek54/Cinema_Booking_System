package cinema.controller;

import cinema.model.Room;
import cinema.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Room room) {
        try {
            return ResponseEntity.ok(roomService.addRoom(room));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}