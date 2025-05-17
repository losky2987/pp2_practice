package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.dto.GateInfoUpdateForm;
import losky2987.pp2_practice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String login(Model model, @AuthenticationPrincipal OAuth2User principal){
        String userId = principal.getAttributes().get("id").toString();
        if (!adminService.isAdminExists(userId)) {
            return "redirect:/error/NotAdminException";
        }
        model.addAttribute("gateExists", false);
        return "admin";
    }

    @PostMapping("/admin/search")
    public String searchResult(Model model) {
        model.addAttribute("gateExists", true);
        Gate gate = new Gate.GateBuilder().setNumber("G01").setFlights(List.of(new Flight.FlightBuilder().setNumber("CA99").setDestination("Hongkong").setDepartureTime(LocalTime.of(11,33 )).build())).build();
        model.addAttribute("gate", gate);
        return "admin";
    }

    @PostMapping("/admin/edit")
    public String editResult(@ModelAttribute GateInfoUpdateForm newInfo, Model model) {
        model.addAttribute("gateExists", false);
        return "admin";
    }
}
