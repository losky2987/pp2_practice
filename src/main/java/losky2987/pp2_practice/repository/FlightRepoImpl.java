package losky2987.pp2_practice.repository;

import losky2987.pp2_practice.db.FlightDB;
import losky2987.pp2_practice.domain.Flight;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
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

    @Override
    public List<Flight> findFlightsByDepartureTime(LocalTime departureTime) {
        return flightDB.findFlightsByDepartureTime(departureTime);
    }

    @Override
    public List<Flight> findFlightsByGateNumber(String gateNumber) {
        return flightDB.findFlightsByGateNumber(gateNumber);
    }
}
