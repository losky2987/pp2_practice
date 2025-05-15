package losky2987.pp2_practice.controller;

import jakarta.validation.Valid;
import losky2987.pp2_practice.config.AdminOnly;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.dto.GateInfoUpdateForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalTime;

@Controller
@AdminOnly
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String login(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String userId = principal.getAttributes().get("id").toString();
        return "redirect:/admin/" + userId;
    }

    @GetMapping("/{userId}")
    public String adminIndex(@PathVariable("userId") String userId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        model.addAttribute("userId", userId);
        model.addAttribute("gateExists", false);
        return "admin";
    }

    @PostMapping("/{userId}/search")
    public String searchResult(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("gateExists", true);
        Gate gate = new Gate.GateBuilder().setNumber("G01").setFlightNumber("SD111").setBoardingTime(LocalTime.of(12,44)).build();
        model.addAttribute("gate", gate);
        return "admin";
    }

    @PostMapping("/{userId}/edit")
    public String editResult(@PathVariable("userId") String userId, @ModelAttribute GateInfoUpdateForm newInfo, Model model) {
        System.out.println(newInfo);
        model.addAttribute("userId", userId);
        model.addAttribute("gateExists", false);
        return "admin";
    }
}
