package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdminDB extends CrudRepository<Admin, String> {
    List<Admin> findAll();
    Admin findAdminByid(String id);
}
