package losky2987.pp2_practice.persistence.repositoryImpl;

import losky2987.pp2_practice.persistence.db.AdminDB;
import losky2987.pp2_practice.domain.Admin;
import losky2987.pp2_practice.domain.repository.AdminRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepoImpl implements AdminRepo {
    private final AdminDB adminDB;

    public AdminRepoImpl(AdminDB adminDB) {
        this.adminDB = adminDB;
    }

    @Override
    public Admin findAdminByOauth2Id(long id) {
        return adminDB.findAdminByOauth2Id(id);
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
