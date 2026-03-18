package example;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.webauthn.api.CredentialRecord;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @MockitoBean
    UserCredentialRepository userCreds;

    @MockitoBean
    PublicKeyCredentialUserEntityRepository userEntities;

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

    @Test
    @WithMockUser(authorities = FactorGrantedAuthority.PASSWORD_AUTHORITY)
    void whenUserHasPasswordFactorAndWebAuthnRegisteredThenRedirectsToLogin() throws Exception {
        setupWebauthnRegistered();
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?factor.type=ott&factor.type=webauthn&factor.reason=missing&factor.reason=missing"));
    }

    @Test
    @WithMockUser(authorities = FactorGrantedAuthority.OTT_AUTHORITY)
    void whenUserHasOttFactorAndWebAuthnRegisteredThenRedirectsToLogin() throws Exception {
        setupWebauthnRegistered();
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?factor.type=password&factor.type=webauthn&factor.reason=missing&factor.reason=missing"));
    }

    private void setupWebauthnRegistered() {
        PublicKeyCredentialUserEntity entity = mock(PublicKeyCredentialUserEntity.class);
        when(this.userEntities.findByUsername(any())).thenReturn(entity);
        when(this.userCreds.findByUserId(any())).thenReturn(List.of(mock(CredentialRecord.class)));
    }
}
