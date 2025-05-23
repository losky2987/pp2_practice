package losky2987.pp2_practice.service;

import losky2987.pp2_practice.domain.Admin;
import losky2987.pp2_practice.domain.repository.AdminRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public synchronized Admin save(Admin admin) {
        return adminRepo.save(admin);
    }

    public synchronized Admin save(long oauth2Id) {
        Admin admin = new Admin(null, oauth2Id);
        return save(admin);
    }

    public Admin findAdminByOauth2Id(long id) {
        return adminRepo.findAdminByOauth2Id(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public boolean isAdminExist(long oauth2Id) {
        return adminRepo.findAdminByOauth2Id(oauth2Id) != null;
    }
}
