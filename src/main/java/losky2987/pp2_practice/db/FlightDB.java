package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightDB extends CrudRepository<Flight, String> {
}
