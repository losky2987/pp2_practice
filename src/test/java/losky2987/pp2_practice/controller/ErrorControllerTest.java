package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.config.SpringSecurityConfig;
import losky2987.pp2_practice.tools.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ErrorController.class)
@Import(SpringSecurityConfig.class)
public class ErrorControllerTest {
    @Autowired
    MockMvc mockMvc;

    private final Map<String, String> errors = new HashMap<>(Map.of(
            "UnknownException", "Error: there are someting goes wrong, but cannot be identified.",
            "NotAdminException", "Error: you cannot visit this page because of you're not admin.",
            "GateNotExistException", "Error: this gate is not exist, check your input again or contact admin.",
            "FlightNotExistException", "Error: this flight is not exist, check your input again or contact admin."
    ));

    @Test
    @WithMockOAuth2User(id=11112222)
    @DisplayName("Test: error redirection as expect")
    void testErrorRedirect() throws Exception {
        for (String key : errors.keySet()) {
            mockMvc.perform(get("/error/" + key))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(view().name("error"))
                    .andExpect(model().attribute("errorMsg", errors.get(key)));
        }
    }
}
