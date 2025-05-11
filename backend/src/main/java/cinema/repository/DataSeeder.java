package cinema.repository;

import cinema.model.Room;
import cinema.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepo;

    public DataSeeder(RoomRepository roomRepo) { this.roomRepo = roomRepo; }

    @Override public void run(String... args) {
        if (roomRepo.findAll().isEmpty()) {
            roomRepo.save(new Room(null, "Sala 1", 60));
            roomRepo.save(new Room(null, "VIP", 30));
        }
    }
}
