package losky2987.pp2_practice.repository;

import losky2987.pp2_practice.domain.Admin;

import java.util.List;

public interface AdminRepo {
    Admin findAdminByOauth2Id(long id);
    Admin save(Admin admin);
    List<Admin> findAll();
}
