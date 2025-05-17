package losky2987.pp2_practice.db;

import losky2987.pp2_practice.domain.Admin;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdminDB extends CrudRepository<Admin, String> {
    List<Admin> findAll();
    Admin save(Admin admin);
//    @Query("select a from admin a where a.id = :id")
    Admin findAdminById(String id);
}
