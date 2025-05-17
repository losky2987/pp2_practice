package losky2987.pp2_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController {
    private final Map<String, String> errors = new HashMap<>(Map.of(
            "UnknownException", "Error: there are someting goes wrong, but cannot be identified.",
            "NotAdminException", "Error: you cannot visit this page because of you're not admin.",
            "GateNotExistException", "Error: this gate is not exist, check your input again or contact admin.",
            "FlightNotExistException", "Error: this flight is not exist, check your input again or contact admin."
    ));

    @GetMapping("/error/{errorType}")
    public String errorType(@PathVariable String errorType, Model model) {
        if (!errors.containsKey(errorType)) {
            model.addAttribute("errorMsg", errors.get("UnknownException"));
            return "error";
        }
        model.addAttribute("errorMsg", errors.get(errorType));
        return "error";
    }
}
