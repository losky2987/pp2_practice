package losky2987.pp2_practice.domain_repo;

import losky2987.pp2_practice.db.FlightDB;
import losky2987.pp2_practice.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightRepoImpl implements FlightRepo {
    private final FlightDB flightDB;

    public FlightRepoImpl(FlightDB flightDB) {
        this.flightDB = flightDB;
    }

    @Override
    public List<Flight> findAll() {
        return flightDB.findAll();
    }

    @Override
    public Flight save(Flight flight) {
        return flightDB.save(flight);
    }

    @Override
    public Flight findFlightByNumber(String flightNumber) {
        return flightDB.findFlightByNumber(flightNumber);
    }

    @Override
    public List<Flight> findFlightsByDestination(String destination) {
        return flightDB.findFlightsByDestination(destination);
    }
}
