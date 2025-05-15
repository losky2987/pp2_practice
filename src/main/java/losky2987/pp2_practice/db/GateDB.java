package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Gate;
import org.springframework.data.repository.CrudRepository;

public interface GateDB extends CrudRepository<Gate, String> {
}
