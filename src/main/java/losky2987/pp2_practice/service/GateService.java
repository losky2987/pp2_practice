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

    public synchronized Gate save(Gate gate) {
        return gateRepo.save(gate);
    }

    public synchronized Gate save(String gateNumber) {
        Gate gate = new Gate(null, gateNumber);
        return gateRepo.save(gate);
    }

    public Gate addGate(Gate gate) {
        return gateRepo.save(gate);
    }

    public Gate addGate(String gateNumber) {
        return save(gateNumber);
    }

    public Gate findGateByNumber(String number) {
        return gateRepo.findGateByNumber(number);
    }

    public List<Gate> getAllGates() {
        return gateRepo.findAll();
    }
}
