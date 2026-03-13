package example;

public class HttpSecurity {
    private boolean formLogin = true;

    public boolean isFormLogin() {
        return formLogin;
    }

    public void setFormLogin(boolean formLogin) {
        this.formLogin = formLogin;
    }
}
