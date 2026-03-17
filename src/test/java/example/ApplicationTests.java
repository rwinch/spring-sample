package example;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = FactorGrantedAuthority.WEBAUTHN_AUTHORITY)
    void whenUserHasWebauthnFactorThenAccessGranted() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = FactorGrantedAuthority.PASSWORD_AUTHORITY)
    void whenUserHasPasswordFactorThenRedirectsToLogin() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?factor.type=ott&factor.reason=missing"));
    }

    @Test
    @WithMockUser(authorities = FactorGrantedAuthority.OTT_AUTHORITY)
    void whenUserHasOttFactorThenRedirectsToLogin() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?factor.type=password&factor.reason=missing"));
    }

    @Test
    @WithMockUser(authorities = {FactorGrantedAuthority.OTT_AUTHORITY, FactorGrantedAuthority.PASSWORD_AUTHORITY})
    void whenUserHasPasswordAndOttFactorsThenAccessGranted() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
