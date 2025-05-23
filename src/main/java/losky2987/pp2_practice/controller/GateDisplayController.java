package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.controller.dto.GateInfoDTO;
import losky2987.pp2_practice.service.FlightService;
import losky2987.pp2_practice.service.GateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class GateDisplayController {
    private final GateService gateService;
    private final FlightService flightService;

    public GateDisplayController(GateService gateService, FlightService flightService) {
        this.gateService = gateService;
        this.flightService = flightService;
    }

    private final Map<String, Object> attributes = new HashMap<>();
    private boolean isFirstTime = true;

    private void initAttributes() {
        attributes.put("toastMsg", null);
        attributes.put("ui_gates", false);
        attributes.put("ui_gate", false);
        attributes.put("allGates", null);
        attributes.put("gateInfo", null);
    }

    private Map<String, Object> getAttributes() {
        return attributes;
    }

    private void setAttributes(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    private void setUI(String uiName) {
        List<String> uiList = List.of("ui_gates", "ui_gate");
        for (String ui : uiList) {
            if (ui.equals(uiName)) {
                attributes.put(ui, true);
            } else {
                attributes.put(ui, false);
            }
        }
    }

    @GetMapping("/gate")
    public String gate(Model model) {
        if (isFirstTime) {
            initAttributes();
            isFirstTime = false;
        }
        setAttributes("allGates", gateService.getAllGates().stream().sorted(Comparator.comparing(Gate::getNumber)).toList());
        setUI("ui_gates");
        model.addAllAttributes(getAttributes());
        return "gate_display";
    }

    @GetMapping("/gate/{number}")
    public String display(@PathVariable("number") String number, Model model) {
        if (isFirstTime) {
            initAttributes();
            isFirstTime = false;
        }
        Gate gate = gateService.findGateByNumber(number);
        if (gate == null) {
            setAttributes("toastMsg", "Gate " + number + " not found");
            setUI("ui_gates");
            return "redirect:/gate";
        }
        setUI("ui_gate");
        if (flightService.getNextFlightByGate(number) == null) {
            setAttributes("toastMsg", "Gate " + number + " has no more flights");
            setUI("ui_gates");
            return "redirect:/gate";
        }
        setAttributes("gateInfo", new GateInfoDTO(number, flightService.getNextFlightByGate(number)));
        model.addAllAttributes(getAttributes());
        return "gate_display";
    }
}
