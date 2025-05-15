package losky2987.pp2_practice.service;

import jakarta.validation.constraints.NotNull;
import losky2987.pp2_practice.domain.Admin;
import losky2987.pp2_practice.domain_repo.AdminRepo;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public Admin saveAdmin(@NotNull Admin admin) {
        return adminRepo.save(admin);
    }

    public boolean isAdminExists(@NotNull Admin admin) {
        return adminRepo.findAdminById(admin.getId()) != null;
    }
}
