package example;

public class SecurityDsl extends AbstractSecurityDsl {

    @Override
    public void register(HttpSecurity http) {
        http.setFormLogin(true);
    }
}
