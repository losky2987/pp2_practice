package losky2987.pp2_practice.service;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Admin;
import losky2987.pp2_practice.repository.AdminRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public Admin save(@NotNull Admin admin) {
        return adminRepo.save(admin);
    }

    @Transactional
    public synchronized Admin addAdmin(String id) {
        Admin admin = new Admin(id);
        return save(admin);
    }

    public Admin findAdminById(@NotNull String id) {
        return adminRepo.findAdminById(id);
    }

    public boolean isAdminExists(String id) {
        return adminRepo.findAdminById(id) != null;
    }
}
