package losky2987.pp2_practice.controller;

import losky2987.pp2_practice.config.SpringSecurityConfig;
import losky2987.pp2_practice.domain.Admin;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


    // this test is only for link /admin
    @Test
    @WithMockOAuth2User(id = 65465493)
    @DisplayName("test legally user can visit admin")
    void canVisitAdmin() throws Exception {
        long id = 65465493;
        Admin admin = new Admin(null, id);
        when(adminService.findAdminByOauth2Id(id)).thenReturn(admin);
        when(adminService.isAdminExist(id)).thenReturn(true);
        mvc.perform(get("/admin")).andExpect(view().name("admin"))
        .andExpect(status().is2xxSuccessful());
    }

    // this test is only for link /admin
    @Test
    @WithMockOAuth2User(id = 12345678)
    @DisplayName("test ilegally user cannot visit admin")
    void cannotVisitAdmin() throws Exception {
        long id = 12345678;
        Admin admin = new Admin(null, id);
        when(adminService.findAdminByOauth2Id(id)).thenReturn(admin);
        when(adminService.isAdminExist(id)).thenReturn(false);
        mvc.perform(get("/admin")).andExpect(redirectedUrl("/error/NotAdminException"))
                .andExpect(status().is3xxRedirection());
    }
}
