package losky2987.pp2_practice.controller;

import jakarta.servlet.http.HttpSession;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.controller.dto.FlightInfoDTO;
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
        Map<String, Object> uiAttributes = new HashMap<>(Map.of(
                "ui_index", false,
                "ui_flights", false,
                "ui_flights_add", true,
                "ui_flights_update", false,
                "ui_gates", false,
                "ui_gates_add", true
        ));
        attributes.put("userId", null);
        attributes.put("flightInfo", new FlightInfoDTO("", "", LocalTime.of(0,0 ), ""));
        attributes.putAll(uiAttributes);
        attributes.put("currentFlights",null);
        attributes.put("allFlights",null);
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

    public void setGatesTab(String tabName) {
        List<String> tabNames = List.of("ui_gates_add");
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

    @GetMapping("/")
    public String jump() {
        return "redirect:/gate";
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
            changeAttribute("toastMsg", "You are logged in");
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

    @GetMapping("/admin/flights")
    public String ui_flights(Model model, HttpSession session) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        setUI("ui_flights");
        setFlightsTab("ui_flights_add");
        changeAttribute("allFlights", flightService.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList());
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/flights/add")
    public String addFlight_post(Model model, HttpSession session, @ModelAttribute FlightInfoDTO flightInfoDTO) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        Flight flight = new Flight(null, flightInfoDTO.getFlightNumber(), flightInfoDTO.getDestination(), flightInfoDTO.getDepartureTime(), flightInfoDTO.getGateNumber());
        if (flightService.addFlight(flight) != null) {
            changeAttribute("toastMsg", "Flight added");
        }else{
            changeAttribute("toastMsg", "Flight not added");
        }
        changeAttribute("allFlights", flightService.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList());
        setUI("ui_flights");
        setFlightsTab("ui_flights_add");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @GetMapping("/admin/flights/search")
    public String searchFlight(Model model, HttpSession session, @RequestParam("flightNumber") String flightNumber) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        Flight flight = flightService.findFlightByNumber(flightNumber);
        if (flight == null) {
            changeAttribute("toastMsg", "Flight not found");
        } else {
            changeAttribute("toastMsg", "Flight found");
            FlightInfoDTO flightInfoDTO = new FlightInfoDTO(flight.getNumber(), flight.getDestination(), flight.getDepartureTime(), flight.getGateNumber());
            changeAttribute("flightInfo", flightInfoDTO);
        }
        changeAttribute("allFlights", flightService.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList());
        setUI("ui_flights");
        setFlightsTab("ui_flights_update");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/flights/update")
    public String updateFlight_post(Model model, HttpSession session, @ModelAttribute FlightInfoDTO flightInfoDTO) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        Flight flight = flightService.findFlightByNumber(flightInfoDTO.getFlightNumber());
        Flight updatedFlight = new Flight(flight.getId(), flightInfoDTO.getFlightNumber(), flightInfoDTO.getDestination(), flightInfoDTO.getDepartureTime(), flightInfoDTO.getGateNumber());
        if (flightService.updateFlight(updatedFlight) != null) {
            changeAttribute("toastMsg", "Flight updated");
        } else {
            changeAttribute("toastMsg", "Flight not updated");
        }
        changeAttribute("flightInfo", new FlightInfoDTO("","",LocalTime.of(0,0),""));
        changeAttribute("allFlights", flightService.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureTime)).toList());
        setUI("ui_flights");
        setFlightsTab("ui_flights_update");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @GetMapping("/admin/gates")
    public String ui_gates(Model model, HttpSession session) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        changeAttribute("allGates", gateService.getAllGates().stream().sorted(Comparator.comparing(Gate::getNumber)).toList());
        setUI("ui_gates");
        setGatesTab("ui_gates_add");
        model.addAllAttributes(getAttributes());
        return "admin";
    }

    @PostMapping("/admin/gates/add")
    public String addGate(Model model, HttpSession session, @RequestParam("gateNumber") String gateNumber) {
        clearToastMsg();
        if (session.getAttribute("userId") == null) {
            return "redirect:/admin";
        }
        Gate gate = new Gate(null, gateNumber);
        if (gateService.addGate(gate) != null) {
            changeAttribute("toastMsg", "Gate added");
        } else {
            changeAttribute("toastMsg", "Gate not added");
        }
        changeAttribute("allGates", gateService.getAllGates().stream().sorted(Comparator.comparing(Gate::getNumber)).toList());
        setUI("ui_gates");
        setGatesTab("ui_gates_add");
        model.addAllAttributes(getAttributes());
        return "admin";
    }
}
