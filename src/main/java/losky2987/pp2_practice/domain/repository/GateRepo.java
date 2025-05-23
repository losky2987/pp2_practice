package losky2987.pp2_practice.domain.repository;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;

import java.util.List;

public interface GateRepo {
    List<Gate> findAll();
    Gate save(Gate gate);
    Gate findGateByNumber(String number);
}
