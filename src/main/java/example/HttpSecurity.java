package example;

import java.util.function.Consumer;

public class HttpSecurity {
    private FormLogin formLogin = new FormLogin();

    FormLogin getFormLogin() {
        return formLogin;
    }

    public HttpSecurity formLogin(Consumer<FormLogin> formLoginCustomizer) {
        formLoginCustomizer.accept(this.formLogin);
        return this;
    }
}
