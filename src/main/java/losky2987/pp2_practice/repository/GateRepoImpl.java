package losky2987.pp2_practice.repository;

import losky2987.pp2_practice.db.GateDB;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GateRepoImpl implements GateRepo {
    private final GateDB gateDB;

    public GateRepoImpl(GateDB gateDB) {
        this.gateDB = gateDB;
    }

    @Override
    public List<Gate> findAll() {
        return gateDB.findAll();
    }

    @Override
    public Gate findGateByNumber(String number) {
        return gateDB.findGateByNumber(number);
    }

    @Override
    public Gate save(Gate gate) {
        return gateDB.save(gate);
    }

    @Override
    public List<Flight> findFlightsByNumber(String gateNumber) {
        return gateDB.findFlightsByNumber(gateNumber);
    }
}
