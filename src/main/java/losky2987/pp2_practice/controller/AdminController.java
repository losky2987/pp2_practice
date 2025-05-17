package losky2987.pp2_practice.controller;

import jakarta.servlet.http.HttpSession;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.dto.FlightInfo;
import losky2987.pp2_practice.dto.GateInfo;
import losky2987.pp2_practice.service.AdminService;
import losky2987.pp2_practice.service.FlightService;
import losky2987.pp2_practice.service.GateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final GateService gateService;
    private final FlightService flightService;
    private final Map<String, Object> attributes = new HashMap<>();
    private boolean firstVisit = true;

    public AdminController(AdminService adminService, GateService gateService, FlightService flightService) {
        this.adminService = adminService;
        this.gateService = gateService;
        this.flightService = flightService;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void initialAttributes() {
        attributes.put("errorAlert",false);
        attributes.put("errorAlertMsg","");
        attributes.put("div_selectFlight",false);
        attributes.put("gateInfo",null);
        attributes.put("div_editFlight",false);
        attributes.put("flightInfo",null);
        attributes.put("tipBox", false);
        attributes.put("tipBoxMsg", "");
    }

    public void changeAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    @GetMapping("/admin")
    public String login(Model model, @AuthenticationPrincipal OAuth2User principal, HttpSession session) {
        String userId = principal.getAttributes().get("id").toString();
        if (!adminService.isAdminExists(userId)) {
            return "redirect:/error/NotAdminException";
        }
        session.setAttribute("userId", userId);
        if (firstVisit) {
            initialAttributes();
            firstVisit = false;
        }
        Map<String, Object> attrs = getAttributes();
        model.addAllAttributes(attrs);
        return "admin";
    }

    @PostMapping("/admin/gate/search")
    public String searchGateResult(Model model, HttpSession session, @RequestParam("gateNumber") String gateNumber) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/error/NotLoginViaOAuth2Exception";
        }
        if (gateService.findGateByNumber(gateNumber) == null) {
            changeAttribute("errorAlert",true);
            changeAttribute("errorAlertMsg","Error: the gate number " + gateNumber + " does not exist.");
            return "redirect:/admin";
        }
        changeAttribute("div_selectFlight",true);
        GateInfo gateInfo = new GateInfo(gateNumber, gateService.findFlightsByGateNumber(gateNumber));
        changeAttribute("gateInfo",gateInfo);
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/gate/flight/search")
    public String searchFlightResult(Model model, HttpSession session, @RequestParam("selectedFlight") String flightNumber) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/error/NotLoginViaOAuth2Exception";
        }
        if (flightService.findFlightByNumber(flightNumber) == null) {
            changeAttribute("errorAlert",true);
            changeAttribute("errorAlertMsg","Error: the flight number " + flightNumber + " does not exist.");
            return "admin";
        }
        changeAttribute("div_editFlight",true);
        FlightInfo flightInfo = new FlightInfo(flightNumber, flightService.findFlightByNumber(flightNumber).getDestination(), flightService.findFlightByNumber(flightNumber).getDepartureTime());
        changeAttribute("flightInfo", flightInfo);
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/edit")
    public String editResult(@ModelAttribute FlightInfo newInfo, Model model) {
        Flight flight = flightService.updateFlightInfo(newInfo);
        Gate gate = (Gate) model.getAttribute("gateInfo");
        assert gate != null;
        gateService.updateFlightsByNumber(gate.getNumber(), List.of(flight));
        changeAttribute("tipBox",true);
        changeAttribute("tipBoxMsg","Successfully updated the flight info.");
        return "admin";
    }
}
