package losky2987.pp2_practice.service;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.dto.FlightInfo;
import losky2987.pp2_practice.repository.FlightRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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

    public synchronized Flight addFlight(String number, String destination, LocalTime departureTime, String gateNumber) {
        Flight flight = new Flight(number, destination, departureTime, gateNumber);
        return save(flight);
    }

    public Flight updateFlight(String number, String destination, LocalTime departureTime, String gateNumber) {
        if (flightRepo.findFlightByNumber(number) != null) {
            return null;
        }
        return addFlight(number, destination, departureTime, gateNumber);
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

    public Flight updateFlightInfo(FlightInfo flightInfo) {
        Flight flight = flightRepo.findFlightByNumber(flightInfo.getFlightNumber());
        return updateFlight(flightInfo.getFlightNumber(), flightInfo.getDestination(), flightInfo.getDepartureTime(), flight.getGateNumber());
    }

    public Gate getGateByFlightNumber(String flightNumber) {
        Flight flight = flightRepo.findFlightByNumber(flightNumber);
        if (flight == null) {
            return null;
        }
        return new Gate(flight.getGateNumber());
    }
}
