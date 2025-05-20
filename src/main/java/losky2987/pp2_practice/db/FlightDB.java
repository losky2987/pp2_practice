package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Flight;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.List;

public interface FlightDB extends CrudRepository<Flight, String> {
    List<Flight> findAll();
    Flight save(Flight flight);
    Flight findFlightByNumber(String flightNumber);
    List<Flight> findFlightsByDestination(String destination);
    List<Flight> findFlightsByDepartureTime(LocalTime departureTime);
    List<Flight> findFlightsByGateNumber(String gateNumber);
}
