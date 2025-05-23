package losky2987.pp2_practice.service;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.domain.repository.FlightRepo;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepo flightRepo;

    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    public Flight save(Flight flight) {
        return flightRepo.save(flight);
    }

    public synchronized Flight addFlight(Long id, String number, String destination, LocalTime departureTime, String gateNumber) {
        Flight flight = new Flight(id, number, destination, departureTime, gateNumber);
        return save(flight);
    }

    public synchronized Flight addFlight(Flight flight) {
        return save(flight);
    }

    public Flight updateFlight(Long id, String number, String destination, LocalTime departureTime, String gateNumber) {
        if (flightRepo.findFlightByNumber(number) != null) {
            return null;
        }
        return addFlight(id, number, destination, departureTime, gateNumber);
    }

    public Flight updateFlight(Flight flight) {
        return save(flight);
    }

    public boolean isFlightExist(String number) {
        return flightRepo.findFlightByNumber(number) != null;
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public List<Flight> getFlightsHasSameDestination(String destination) {
        return flightRepo.findFlightsByDestination(destination);
    }

    public Flight findFlightByNumber(String number) {
        return flightRepo.findFlightByNumber(number);
    }

    public Gate getGateByFlightNumber(String flightNumber) {
        Flight flight = flightRepo.findFlightByNumber(flightNumber);
        if (flight == null) {
            return null;
        }
        return new Gate(null, flight.getGateNumber());
    }

    public List<Flight> getFlightsByDepartureTime(LocalTime departureTime) {
        return flightRepo.findFlightsByDepartureTime(departureTime);
    }

    public List<Flight> getFlightsByGateNumber(String gateNumber) {
        return flightRepo.findFlightsByGateNumber(gateNumber);
    }

    public Flight getNextFlightByGate(String gateNumber) {
        List<Flight> flights = getFlightsByGateNumber(gateNumber).stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList();
        if (flights.isEmpty()) {
            return null;
        }
        return flights.getFirst();
    }
}
