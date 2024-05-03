package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
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
    private final USBFlashDriveInfoRetrievalService usbService;

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("email");
    private final PasswordField masterPassword = new PasswordField("master password");
    private final PasswordField confirmMasterPassword = new PasswordField("confirm master password");
    private final H1 mainText = new H1("Register | Password Manager");
    private final H2 USBFlashDriveText = new H2("Insert your USB flash drive to complete registration");

    Button nextButton = new Button("Next");

    public RegisterView(AuthenticationFacade authenticationFacade, USBFlashDriveInfoRetrievalService usbService) {
        this.authenticationFacade = authenticationFacade;
        this.usbService = usbService;

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                mainText,
                firstName,
                lastName,
                email,
//                masterPassword,
//                confirmMasterPassword,
                configureNextButton()
        );
        log.info(this.usbService.getUSBFlashDriveInfo());
    }

    private Component configureNextButton() {
        masterPassword.setReadOnly(true);
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.addClickShortcut(Key.ENTER);

        nextButton.addClickListener(event -> {
//            authenticationFacade.registerUser(
//                    firstName.getValue(),
//                    lastName.getValue(),
//                    email.getValue(),
//                    masterPassword.getValue(),
//                    confirmMasterPassword.getValue()
//            );
//            UI.getCurrent().navigate("");
            if (!validateData()) {
                validateData();
            } else {
                updateView();
            }
        });

        return nextButton;
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

    private void updateView() {
        remove(mainText, firstName, lastName, email, nextButton);
        add(mainText, USBFlashDriveText, masterPassword);

    }
}
