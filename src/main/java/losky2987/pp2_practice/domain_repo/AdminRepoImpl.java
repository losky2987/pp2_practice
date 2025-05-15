package losky2987.pp2_practice.domain_repo;

import losky2987.pp2_practice.db.AdminDB;
import losky2987.pp2_practice.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepoImpl implements AdminRepo {
    private final AdminDB adminDB;

    public AdminRepoImpl(AdminDB adminDB) {
        this.adminDB = adminDB;
    }

    @Override
    public Admin findAdminById(String id) {
        return adminDB.findAdminByid(id);
    }

    @Override
    public Admin save(Admin admin) {
        return adminDB.save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return adminDB.findAll();
    }
}
