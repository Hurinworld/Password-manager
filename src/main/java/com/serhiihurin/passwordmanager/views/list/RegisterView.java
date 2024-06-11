package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

@Route("register")
@AnonymousAllowed
@Slf4j
public class RegisterView extends VerticalLayout {
    private final AuthenticationFacade authenticationFacade;
    private final USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade;
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("email");
    private final PasswordField masterPassword = new PasswordField("master password");

    Button registerButton = new Button("Register");

    public RegisterView(
            AuthenticationFacade authenticationFacade,
            USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade
    ) {
        this.authenticationFacade = authenticationFacade;
        this.usbFlashDriveInfoRetrievalFacade = usbFlashDriveInfoRetrievalFacade;

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        configureNextButton();

        add(
                new H1("Register | Password Manager"),
                firstName,
                lastName,
                email,
                registerButton
        );
    }

    private void configureNextButton() {
        masterPassword.setReadOnly(true);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickShortcut(Key.ENTER);

        registerButton.addClickListener(event -> {
            if (!validateData()) {
                validateData();
            } else {
                completeRegistration();
            }
        });
    }

    private void completeRegistration() {
        masterPassword.setValue(usbFlashDriveInfoRetrievalFacade.linkUSBFlashDrive(true));
        authenticationFacade.registerUser(
                firstName.getValue(),
                lastName.getValue(),
                email.getValue(),
                masterPassword.getValue()
        );
        authenticationFacade.authenticateUser(
                AuthenticationRequestDTO.builder()
                        .email(email.getValue())
                        .password(masterPassword.getValue())
                        .build()
        );
        UI.getCurrent().navigate("/home");
    }

    private boolean validateData() {
        if (firstName.getValue().isEmpty()) {
            Notification.show("First name field is required");
            return false;
        } else if (lastName.getValue().isEmpty()) {
            Notification.show("Last name field is required");
            return false;
        } else if (email.getValue().isEmpty()) {
            Notification.show("Email field is required");
            return false;
        }
        return true;
    }
}
