package losky2987.pp2_practice.domain_repo;

import losky2987.pp2_practice.domain.Admin;

import java.util.List;

public interface AdminRepo {
    Admin findAdminById(String id);
    Admin save(Admin admin);
    List<Admin> findAll();
}
