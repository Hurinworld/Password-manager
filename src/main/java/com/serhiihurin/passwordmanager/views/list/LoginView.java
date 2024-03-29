package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | Password Manager")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterListener {
    private final LoginForm loginForm = new LoginForm();
//    private final RouterLink routerLink = new RouterLink("Register", RegisterView.class);

    public LoginView(AuthenticationFacade authenticationFacade) {

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.addLoginListener(
                event -> authenticationFacade.authenticateUser(
                        AuthenticationRequestDTO.builder()
                        .email(event.getUsername())
                        .password(event.getPassword())
                        .build())
        );
        loginForm.setForgotPasswordButtonVisible(false);


        add(
                new H1("Password Manager"),
                loginForm,
                new RouterLink("Register", RegisterView.class)
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
