package losky2987.pp2_practice.controller;

import jakarta.servlet.http.HttpSession;
import losky2987.pp2_practice.domain.Flight;
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

import java.time.LocalTime;
import java.util.Comparator;
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
        attributes.put("gateInfo", new GateInfo(""));
        attributes.put("flightInfo", new FlightInfo("", "", LocalTime.of(0,0 ), ""));
        attributes.put("ui_flights", false);
        attributes.put("ui_flights_add", true); // active
        attributes.put("ui_flights_update", false); // active
        attributes.put("ui_gates", false);
        attributes.put("ui_index", false);
        attributes.put("currentFlights",null);
        attributes.put("toastMsg", null);
    }

    public void changeAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    public void setUI(String uiName){
        List<String> uiNames = List.of("ui_index", "ui_flights", "ui_gates"); // maintenance this list to make ui switch convenient
        for(String str : uiNames){
            if (str.equals(uiName)){
                attributes.put(str, true);
            } else {
                attributes.put(str, false);
            }
        }
    }

    public void setFlightsTab(String tabName) {
        List<String> tabNames = List.of("ui_flights_add", "ui_flights_update");
        for(String str : tabNames){
            if (str.equals(tabName)){
                attributes.put(str, true);
            } else {
                attributes.put(str, false);
            }
        }
    }

    public void clearToastMsg() {
        attributes.put("toastMsg", null);
    }

    @GetMapping("/admin")
    public String login(Model model, @AuthenticationPrincipal OAuth2User principal, HttpSession session) {
        clearToastMsg();
        String id = principal.getAttributes().get("id").toString();
        if (id == null) {
            return "redirect:/oauth2/authorization/github";
        }
        long userId = Long.parseLong(id);
        if (!adminService.isAdminExist(userId)) {
            return "redirect:/oauth2/authorization/github";
        }
        session.setAttribute("userId", userId);
        changeAttribute("userId", userId);
        if (firstVisit) {
            initialAttributes();
            firstVisit = false;
        }
        setUI("ui_index");
        changeAttribute("currentFlights", flightService.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList());
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin")
    public String returnIndex(){
        return "redirect:/admin";
    }

    @PostMapping("/admin/flights")
    public String ui_flights_post(Model model, HttpSession session) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/oauth2/authorization/github";
        }
        setUI("ui_flights");
        setFlightsTab("ui_flights_add");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/flights/add")
    public String addFlight_post(Model model, HttpSession session, @ModelAttribute FlightInfo flightInfo) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/oauth2/authorization/github";
        }
        Flight flight = new Flight(null, flightInfo.getFlightNumber(), flightInfo.getDestination(), flightInfo.getDepartureTime(), flightInfo.getGateNumber());
        if (flightService.addFlight(flight) != null) {
            changeAttribute("toastMsg", "Flight added");
        }else{
            changeAttribute("toastMsg", "Flight not added");
        }
        setUI("ui_flights");
        setFlightsTab("ui_flights_add");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/flights/search")
    public String searchFlight_post(Model model, HttpSession session, @RequestParam("flightNumber") String flightNumber) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/oauth2/authorization/github";
        }
        Flight flight = flightService.findFlightByNumber(flightNumber);
        if (flight == null) {
            changeAttribute("toastMsg", "Flight not found");
        } else {
            changeAttribute("toastMsg", "Flight found");
            FlightInfo flightInfo = new FlightInfo(flight.getNumber(), flight.getDestination(), flight.getDepartureTime(), flight.getGateNumber());
            changeAttribute("flightInfo", flightInfo);
        }
        setUI("ui_flights");
        setFlightsTab("ui_flights_update");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/flights/update")
    public String updateFlight_post(Model model, HttpSession session, @ModelAttribute FlightInfo flightInfo) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/oauth2/authorization/github";
        }
        Flight flight = flightService.findFlightByNumber(flightInfo.getFlightNumber());
        Flight updatedFlight = new Flight(flight.getId(), flightInfo.getFlightNumber(), flightInfo.getDestination(), flightInfo.getDepartureTime(), flightInfo.getGateNumber());
        if (flightService.updateFlight(updatedFlight) != null) {
            changeAttribute("toastMsg", "Flight updated");
        } else {
            changeAttribute("toastMsg", "Flight not updated");
        }
        changeAttribute("flightInfo", new FlightInfo("","",LocalTime.of(0,0),""));
        setUI("ui_flights");
        setFlightsTab("ui_flights_update");
        model.addAllAttributes(getAttributes());
        return "admin";
    }
}
