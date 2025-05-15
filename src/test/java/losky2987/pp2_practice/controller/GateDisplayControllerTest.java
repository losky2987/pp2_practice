package losky2987.pp2_practice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GateDisplayController.class)
public class GateDisplayControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Test: gate display available")
    void testGateDisplayAvailable() throws Exception {
        mvc.perform(get("/gate/A01")).andExpect(status().isOk());
    }
}
