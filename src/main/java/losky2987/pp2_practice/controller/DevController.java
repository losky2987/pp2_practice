package losky2987.pp2_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dev/")
public class DevController {
    @GetMapping("/available_test")
    public String available_test() {
        return "dev";
    }
}
