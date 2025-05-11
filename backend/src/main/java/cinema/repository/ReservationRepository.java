package cinema.repository;

import cinema.model.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final File file = new File("reservations.json");
    private long nextId = 1;

    public ReservationRepository() {
        load();
        reservations.stream().mapToLong(Reservation::getId).max().ifPresent(max -> nextId = max + 1);
    }

    // -------------------- API --------------------
    public List<Reservation> findAll()            { return new ArrayList<>(reservations); }

    public Reservation save(Reservation r){
        r.setId(nextId++);
        reservations.add(r);
        saveToFile();
        return r;
    }

    // -------------------- I/O --------------------
    private void load(){
        if(!file.exists()) return;
        try{
            reservations.addAll(mapper.readValue(file, new TypeReference<>(){}));
        }catch(IOException e){
            System.err.println("reservations.json uszkodzony → "+e.getMessage());
            File bad = new File("reservations_corrupt_"+System.currentTimeMillis()+".json");
            if(file.renameTo(bad)) System.err.println("Kopia: "+bad.getName());
        }
    }
    private void saveToFile(){
        try{
            File tmp = new File(file.getPath()+".tmp");
            mapper.writerWithDefaultPrettyPrinter().writeValue(tmp, reservations);
            if(!tmp.renameTo(file)){
                throw new IOException("Nie mogę podmienić pliku "+file.getName());
            }
        }catch(IOException e){
            System.err.println("Błąd zapisu rezerwacji: "+e.getMessage());
        }
    }

    public boolean deleteById(Long id){
        boolean removed = reservations.removeIf(r -> r.getId().equals(id));
        if(removed) saveToFile();
        return removed;
    }

}
