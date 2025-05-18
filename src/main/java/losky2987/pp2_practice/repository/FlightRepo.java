package losky2987.pp2_practice.repository;

import losky2987.pp2_practice.domain.Flight;

import java.util.List;

public interface FlightRepo {
    List<Flight> findAll();
    Flight save(Flight flight);
    Flight findFlightByNumber(String flightNumber);
    List<Flight> findFlightsByDestination(String destination);
    List<Flight> findFlightsByDepartureTime(String departureTime);
    List<Flight> findFlightsByGateNumber(String gateNumber);
}
