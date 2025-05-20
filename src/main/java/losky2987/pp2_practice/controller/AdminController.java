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
        attributes.put("userId", null);
        attributes.put("gateInfo", null);
        attributes.put("flightInfo", null);
        attributes.put("ui_viewFlights", false);
        attributes.put("ui_viewGates", false);
        attributes.put("ui_index", false);
        attributes.put("currentFlights",null);
        attributes.put("toastMsg", null);
    }

    public void changeAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    public void setUI(String uiName){
        List<String> uiNames = List.of("ui_index", "ui_viewFlights", "ui_viewGates"); // maintenance this list to make ui switch convenient
        for(String str : uiNames){
            if (str.equals(uiName)){
                attributes.put(str, true);
            } else {
                attributes.put(str, false);
            }
        }
    }

    @GetMapping("/admin")
    public String login(Model model, @AuthenticationPrincipal OAuth2User principal, HttpSession session) {
        String userId = principal.getAttributes().get("id").toString();
        if (!adminService.isAdminExists(userId)) {
            return "redirect:/oauth2/authorization/github";
        }
        session.setAttribute("userId", userId);
        changeAttribute("userId", userId);
        if (firstVisit) {
            initialAttributes();
            firstVisit = false;
        }
        setUI("ui_index");
        changeAttribute("currentFlights", flightService.getAllFlights());
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin")
    public String returnIndex(){
        return "redirect:/admin";
    }

    //    this is an example to use toastMsg, do not delete, remove or change it!
    @PostMapping("/admin/toastMsg")
    public String testToast(Model model, HttpSession session){
        changeAttribute("toastMsg", "this is a test");
        model.addAllAttributes(getAttributes());
        return "admin :: toastFragment";
    }

    @PostMapping("/admin/viewFlights")
    public String ui_viewFlights(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/oauth2/authorization/github";
        }
        setUI("ui_viewFlights");
        model.addAllAttributes(getAttributes());
        return "admin";
    }





    // the functions below was dropped off, reimplement with ui change

    @PostMapping("/admin/gate/search")
    public String searchGateResult(Model model, HttpSession session, @RequestParam("gateNumber") String gateNumber) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/error/NotLoginViaOAuth2Exception";
        }
        if (gateService.findGateByNumber(gateNumber) == null) {
            changeAttribute("msgBox",true);
            changeAttribute("msgAlert","Error: the gate number " + gateNumber + " does not exist.");
            return "redirect:/admin";
        }
        changeAttribute("div_selectFlight",true);
        GateInfo gateInfo = new GateInfo(gateNumber);
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
            changeAttribute("msgBox",true);
            changeAttribute("msgAlert","Error: the flight number " + flightNumber + " does not exist.");
            return "admin";
        }
        changeAttribute("div_editFlight",true);
        FlightInfo flightInfo = new FlightInfo(flightNumber, flightService.findFlightByNumber(flightNumber).getDestination(), flightService.findFlightByNumber(flightNumber).getDepartureTime(), flightService.findFlightByNumber(flightNumber).getGateNumber());
        changeAttribute("flightInfo", flightInfo);
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/edit")
    public String editResult(@ModelAttribute FlightInfo newInfo, Model model) {
        Flight flight = flightService.updateFlightInfo(newInfo);
        Gate gate = (Gate) model.getAttribute("gateInfo");
        assert gate != null;
        flightService.updateFlight(flight.getNumber(), flight.getDestination(), flight.getDepartureTime(), flight.getGateNumber());
        changeAttribute("msgBox",true);
        changeAttribute("msgInfo","Successfully updated the flight info.");
        return "admin";
    }
}
