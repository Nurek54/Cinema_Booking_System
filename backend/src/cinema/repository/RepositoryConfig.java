package cinema.repository;

import cinema.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean public MovieRepository movieRepository()       { return new MovieRepository(); }
    @Bean public ScreeningRepository screeningRepository(){ return new ScreeningRepository(); }
    @Bean public ReservationRepository reservationRepository(){ return new ReservationRepository(); }
    @Bean public RoomRepository roomRepository()         { return new RoomRepository(); }
}
