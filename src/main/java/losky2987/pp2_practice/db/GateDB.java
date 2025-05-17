package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GateDB extends CrudRepository<Gate, String> {
    List<Gate> findAll();
    Gate save(Gate gate);
    Gate findGateByNumber(String number);
    List<Flight> findFlightsByNumber(String gateNumber);
}
