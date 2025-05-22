package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.config.SpringSecurityConfig;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.dto.GateInfo;
import losky2987.pp2_practice.service.FlightService;
import losky2987.pp2_practice.service.GateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GateDisplayController.class)
@Import(SpringSecurityConfig.class)
public class GateDisplayControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    GateService gateService;
    @MockitoBean
    FlightService flightService;


    @Test
    @DisplayName("Test: gate list shows")
    void testGateListShows() throws Exception {
        List<Gate> gates = List.of(
                new Gate(null, "A03"),
                new Gate(null, "C45"),
                new Gate(null, "B65")
        );
        Model model = mock(Model.class);
        when(gateService.getAllGates()).thenReturn(gates);
        model.addAttribute("allGates", gates);
        mvc.perform(get("/gate")).andExpect(status().isOk())
                .andExpect(view().name("gate_display"))
                .andExpect(model().attributeExists("allGates"));
    }

    @Test
    @DisplayName("Test: gate display available")
    void testGateDisplayAvailable() throws Exception {
        Gate gate = new Gate(null,"A01");
        Flight flight = new Flight(null,"SC9876", "Shenyang", LocalTime.of(3,20), "A01");
        when(gateService.findGateByNumber(gate.getNumber())).thenReturn(gate);
        when(flightService.getNextFlightByGate(gate.getNumber())).thenReturn(flight);
        Model model = mock(Model.class);
        model.addAttribute("gateInfo", new GateInfo(gate.getNumber(), flightService.getNextFlightByGate(gate.getNumber())));
        mvc.perform(get("/gate/A01")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test: gate not exist and redirect to /gate")
    void testGateNotFound() throws Exception {
        when(gateService.findGateByNumber("Y01")).thenReturn(null);
        mvc.perform(get("/gate/Y01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/gate"));
    }

    @Test
    @DisplayName("Test: gate exist but has no more flight")
    void testGateFound() throws Exception {
        Gate gate = new Gate(null,"Z01");
        when(flightService.getNextFlightByGate(gate.getNumber())).thenReturn(null);
        mvc.perform(get("/gate/A01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/gate"));
    }
}
