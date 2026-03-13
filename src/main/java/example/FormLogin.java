package example;

import org.springframework.security.core.userdetails.UserDetailsService;

public class FormLogin {
    private boolean enabled = true;
    private UserDetailsService userDetailsService;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
