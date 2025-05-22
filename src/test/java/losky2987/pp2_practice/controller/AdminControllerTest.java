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

    // test for /admin/flights/add

    // test for /admin/flights/search

    // test for /admin/flights/update

    // test for /admin/gates

    // test for /admin/gates/add
}
