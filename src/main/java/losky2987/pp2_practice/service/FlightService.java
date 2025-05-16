package losky2987.pp2_practice.service;

import losky2987.pp2_practice.domain.Flight;
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

    @Transactional
    public synchronized Flight addFlight(String number, String destination, LocalTime departureTime) {
        Flight flight = new Flight.FlightBuilder().setNumber(number).setDestination(destination).setDepartureTime(departureTime).build();
        return save(flight);
    }

    public Flight updateFlight(String number, String destination, LocalTime departureTime) {
        Flight flight = flightRepo.findFlightByNumber(number);
        flight.setDestination(destination);
        flight.setDepartureTime(departureTime);
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

    //feature: update flight info
}
