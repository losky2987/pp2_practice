package losky2987.pp2_practice.service;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.repository.GateRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService {
    private final GateRepo gateRepo;

    public GateService(GateRepo gateRepo) {
        this.gateRepo = gateRepo;
    }

    public Gate save(Gate gate) {
        return gateRepo.save(gate);
    }

    public Gate findGateByNumber(String number) {
        return gateRepo.findGateByNumber(number);
    }

    public List<Flight> findFlightsByGateNumber(String gateNumber) {
        return gateRepo.findFlightsByNumber(gateNumber);
    }

    public Gate updateFlightsByNumber(String gateNumber, List<Flight> flight) {
        Gate gate = findGateByNumber(gateNumber);
        gate.addAllFlights(flight);
        return gateRepo.save(gate);
    }
}
