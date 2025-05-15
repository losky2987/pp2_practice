package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/gate/")
public class GateDisplayController {
    @GetMapping("/{number}")
    public String display(@PathVariable("number") String number, Model model) {
        Gate gate = new Gate.GateBuilder().setNumber(number).setFlights(List.of(new Flight.FlightBuilder().setNumber("HO1607").setDestination("Helsinki").setDepartureTime(LocalTime.of(9,0)).build())).build();
        model.addAttribute("gate", gate);
        return "gate_display";
    }
}
