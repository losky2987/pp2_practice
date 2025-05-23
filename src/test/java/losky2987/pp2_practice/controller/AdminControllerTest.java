package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.config.SpringSecurityConfig;
import losky2987.pp2_practice.domain.Admin;
import losky2987.pp2_practice.domain.Flight;
import losky2987.pp2_practice.domain.Gate;
import losky2987.pp2_practice.service.AdminService;
import losky2987.pp2_practice.service.FlightService;
import losky2987.pp2_practice.service.GateService;
import losky2987.pp2_practice.tools.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(SpringSecurityConfig.class)
public class AdminControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    AdminService adminService;
    @MockitoBean
    GateService gateService;
    @MockitoBean
    FlightService flightService;

    // test for /

    @Test
    @DisplayName("Test: when / visit, then redirect to /gate")
    public void whenVisit() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/gate"));
    }

    // tests for /admin

    @Test
    @WithMockOAuth2User(id=11112222)
    @DisplayName("Test: post /admin will be redirect to /admin")
    public void postAdmin() throws Exception {
        mvc.perform(post("/admin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }


    @Test
    @WithMockOAuth2User(id = 65465493)
    @DisplayName("Test: legally user can visit admin")
    void canVisitAdmin() throws Exception {
        long id = 65465493;
        Admin admin = new Admin(null, id);
        when(adminService.findAdminByOauth2Id(id)).thenReturn(admin);
        when(adminService.isAdminExist(id)).thenReturn(true);
        mvc.perform(get("/admin")).andExpect(view().name("admin"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("admin"));
    }

    @Test
    @WithMockOAuth2User(id=99998888)
    @DisplayName("Test: illegally user cannot visit admin")
    void cannotVisitAdmin() throws Exception {
        long id = 99998888;
        Admin admin = new Admin(null, id);
        when(adminService.findAdminByOauth2Id(id)).thenReturn(admin);
        when(adminService.isAdminExist(id)).thenReturn(false);
        mvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/oauth2/authorization/github"));
    }

    @Test
    @DisplayName("Test: user not login")
    public void userNotLogin() throws Exception {
        mvc.perform(get("/admin").param("userId", (String) null))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/oauth2/authorization/github"));
    }

    // test for /admin/flights
    @Test
    @WithMockOAuth2User(id=65465493)
    @DisplayName("Test: flights can shows")
    public void flightsCanShows() throws Exception {
        List<Flight> flights = List.of(
                new Flight(null, "CU987", "A", LocalTime.of(1,2), "A1"),
                new Flight(null, "TH123", "B", LocalTime.of(2,3),"B2")
        );
        long id = 65465493;
        when(flightService.getAllFlights()).thenReturn(flights);
        mvc.perform(get("/admin/flights").sessionAttr("userId", id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("allFlights"));
    }

    // test for /admin/flights/add
    @Test
    @WithMockOAuth2User(id=65465493)
    @DisplayName("Test: data from ui transfer to backend in FlightInfoDTO")
    public void dataFromUiTransfer() throws Exception {
        when(flightService.addFlight(any(Flight.class))).thenReturn(new Flight(null,null,null, null, null));

        mvc.perform(post("/admin/flights/add")
                        .with(csrf())
                        .sessionAttr("userId", 65465493)
                        .param("flightNumber", "CA123")
                        .param("destination", "Beijing")
                        .param("departureTime", "10:00")
                        .param("gateNumber", "A1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));

        verify(flightService).addFlight(argThat(flight ->
                "CA123".equals(flight.getNumber()) &&
                        "Beijing".equals(flight.getDestination()) &&
                        "10:00".equals(flight.getDepartureTime().toString()) &&
                        "A1".equals(flight.getGateNumber())
        ));
    }

    // test for /admin/flights/search
    @Test
    @DisplayName("Test: the flight cannot searched in db")
    @WithMockOAuth2User(id=65465493)
    public void flightCannotSearchedInDb() throws Exception {
        when(flightService.findFlightByNumber("AC1")).thenReturn(null);

        mvc.perform(get("/admin/flights/search")
                        .with(csrf())
                .sessionAttr("userId", 65465493)
        .param("flightNumber", "AC1"))
                .andExpect(model().attribute("toastMsg", "Flight not found"));
    }

    @Test
    @DisplayName("Test: the flight can searched in db")
    @WithMockOAuth2User(id=65465493)
    public void flightCanSearchedInDb() throws Exception {
        when(flightService.findFlightByNumber("AC1")).thenReturn(new Flight(null, "AC1", "Vancannver", LocalTime.of(1,2), "A1"));

        mvc.perform(get("/admin/flights/search")
                        .with(csrf())
                        .sessionAttr("userId", 65465493)
                        .param("flightNumber", "AC1"))
                .andExpect(model().attribute("toastMsg", "Flight found"));
    }

    // test for /admin/flights/update
    @Test
    @DisplayName("Test: flight info was updated")
    @WithMockOAuth2User(id=65465493)
    public void flightInfoUpdated() throws Exception {
        Flight originalFlight = new Flight(1L, "CA123", "Shanghai", LocalTime.of(9, 0), "B2");
        when(flightService.findFlightByNumber("CA123")).thenReturn(originalFlight);

        when(flightService.updateFlight(any(Flight.class))).thenReturn(
                new Flight(1L, "CA123", "Beijing", LocalTime.of(10, 30), "C3")
        );

        mvc.perform(post("/admin/flights/update")
                        .with(csrf())
                        .sessionAttr("userId", 65465493)
                        .param("flightNumber", "CA123")
                        .param("destination", "Beijing")
                        .param("departureTime", "10:30")  // 格式要匹配 DTO 里的 LocalTime
                        .param("gateNumber", "C3"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));

        verify(flightService).updateFlight(argThat(flight ->
                flight.getId().equals(1L) &&
                        flight.getNumber().equals("CA123") &&
                        flight.getDestination().equals("Beijing") &&
                        flight.getDepartureTime().equals(LocalTime.of(10, 30)) &&
                        flight.getGateNumber().equals("C3")
        ));
    }

    // test for /admin/gates
    @Test
    @DisplayName("Test: gate can be visit")
    @WithMockOAuth2User(id=65465493)
    public void gateCanBeVisit() throws Exception {
        List<Gate> gates = List.of(
                new Gate(null, "A01"),
                new Gate(null, "A02")
        );
        when(gateService.getAllGates()).thenReturn(gates);

        mvc.perform(get("/admin/gates")
                    .with(csrf())
                    .sessionAttr("userId", 65465493))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists( "allGates" ))
                .andExpect(view().name("admin"));
    }

    // test for /admin/gates/add
    @Test
    @WithMockOAuth2User(id=65465493)
    @DisplayName("Test: data from ui transfer to backend with gateNumber")
    public void dataFromUiTransfer2() throws Exception {
        when(gateService.addGate(any(Gate.class))).thenReturn(new Gate(1L, "A5"));
        when(gateService.findGateByNumber("A5")).thenReturn(new Gate(1L, "A5"));

        mvc.perform(post("/admin/gates/add")
                        .with(csrf())
                        .sessionAttr("userId", 65465493)
                        .param("gateNumber", "A5"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));

        assertThat(gateService.findGateByNumber("A5")).isNotNull();
    }
}
