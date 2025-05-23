package losky2987.pp2_practice.persistence.repositoryImpl;

import losky2987.pp2_practice.persistence.db.GateDB;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.domain.repository.GateRepo;
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
    public Gate save(Gate gate) {
        return gateDB.save(gate);
    }

    @Override
    public Gate findGateByNumber(String number) {
        return gateDB.findGateByNumber(number);
    }
}
