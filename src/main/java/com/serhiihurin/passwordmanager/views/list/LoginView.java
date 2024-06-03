package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
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
    private final USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade;
    Button readPasswordButton = new Button("Read Password from USB");

    public LoginView(
            AuthenticationFacade authenticationFacade,
            AuthenticationContext authenticationContext,
            USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade
    ) {
        this.usbFlashDriveInfoRetrievalFacade = usbFlashDriveInfoRetrievalFacade;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        readPasswordButton.addClickListener(event -> fillInPasswordField());

//        loginForm.addLoginListener(
//                event -> {
//                    if (authenticationFacade.authenticateUser(
//                            AuthenticationRequestDTO.builder()
//                                    .email(event.getUsername())
//                                    .password(event.getPassword())
//                                    .build()
//                    ) != null) {
//                        User user = authenticationContext.getAuthenticatedUser(User.class)
//                                .orElseThrow(() -> new RuntimeException("gdsgsdfs"));
//                        log.info("Entered redirection block with user: {}", user.getFirstName());
//                        UI.getCurrent().navigate("/home");
//                    }
//                }
//        );
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);


        add(
                new H1("Password Manager"),
                loginForm,
                readPasswordButton,
                new RouterLink("Don't have an account? Register now", RegisterView.class)
        );

        getElement().executeJs(
                "this.querySelector('vaadin-login-form')" +
                        ".shadowRoot.querySelector('vaadin-password-field')" +
                        ".setAttribute('readonly', true);"
        );
    }

    private void fillInPasswordField() {
        String password = usbFlashDriveInfoRetrievalFacade.linkUSBFlashDrive(null);
        UI.getCurrent().getPage().executeJs(
                "document.querySelector('vaadin-login-form vaadin-password-field').value = $0;",
                password);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
        if (event.getLocation().getQueryParameters().getParameters().containsKey("sessionExpired")) {
            Notification.show("Session expired", 3000, Notification.Position.MIDDLE);
        }
    }
}
