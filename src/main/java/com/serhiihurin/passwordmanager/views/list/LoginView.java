package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.extern.slf4j.Slf4j;

@Route("login")
@PageTitle("Login | Password Manager")
@AnonymousAllowed
@Slf4j
public class LoginView extends VerticalLayout implements BeforeEnterListener {
    private final LoginForm loginForm = new LoginForm();

    public LoginView(AuthenticationFacade authenticationFacade, AuthenticationContext authenticationContext) {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.addLoginListener(
                event -> {
                    if (authenticationFacade.authenticateUser(
                            AuthenticationRequestDTO.builder()
                                    .email(event.getUsername())
                                    .password(event.getPassword())
                                    .build()
                    ) != null) {
                        User user = authenticationContext.getAuthenticatedUser(User.class)
                                .orElseThrow(() -> new RuntimeException("gdsgsdfs"));
                        log.info("Entered redirection block with user: {}", user.getFirstName());
                        UI.getCurrent().navigate("");

                    }
                }
        );
//        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);


        add(
                new H1("Password Manager"),
                loginForm,
                new RouterLink("Don't have an account? Register now", RegisterView.class)
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
