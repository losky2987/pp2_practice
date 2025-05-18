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

    public Gate findGateByNumber(String number) {
        return gateRepo.findGateByNumber(number);
    }
}
