package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.ExistingRecordsOperationType;
import com.serhiihurin.passwordmanager.facade.interfaces.EmailFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import com.vaadin.flow.component.UI;
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

import java.util.Objects;

@Route("link-new-token")
@AnonymousAllowed
public class LinkNewTokenView extends VerticalLayout {
    private String verificationCode;

    private User user;
    private final EmailFacade emailFacade;
    private final USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade;
    private final UserService userService;

    private final EmailField email = new EmailField("email");
    private final PasswordField masterPassword = new PasswordField("master password");
    private final Button sendVerificationCode = new Button("Send code");
    private final TextField verificationCodeField = new TextField("verification code");
    private final Button linkNewUSBToken = new Button("Link new USB Token");

    public LinkNewTokenView(
            EmailFacade emailFacade,
            USBFlashDriveInfoRetrievalFacade usbFlashDriveInfoRetrievalFacade,
            UserService userService
    ) {
        this.emailFacade = emailFacade;
        this.usbFlashDriveInfoRetrievalFacade = usbFlashDriveInfoRetrievalFacade;
        this.userService = userService;

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        masterPassword.setReadOnly(true);

        configureButtons();

        add(
                new H1("USB token changing"),
                new H2("Enter valid email to send verification code"),
                email,
                sendVerificationCode,
                verificationCodeField,
                new H2("Connect your USB flash drive to link"),
                masterPassword,
                linkNewUSBToken
        );
    }

    private void configureButtons() {
        sendVerificationCode.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendVerificationCode.addClickListener(event -> {
            try {
                user = userService.getUserByEmail(email.getValue());
            } catch (RuntimeException e) {
                Notification.show("Invalid email. Try again");
            }
            if (user != null) {
                verificationCode = emailFacade.sendPasswordChangingVerificationCode(email.getValue());
                Notification.show("Sent verification code to email " + email.getValue());
            }
        });

        linkNewUSBToken.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        linkNewUSBToken.addClickListener(event -> {
            if (!Objects.equals(verificationCode, verificationCodeField.getValue())) {
                Notification.show("Invalid verification code. Try again.");
                verificationCode = "";
                UI.getCurrent().navigate("");
            } else {
                usbFlashDriveInfoRetrievalFacade.changeUSBFlashDrive(user, ExistingRecordsOperationType.DECRYPT);
                masterPassword.setValue(usbFlashDriveInfoRetrievalFacade.linkUSBFlashDrive(true));
                user.setMasterPassword(masterPassword.getValue());
                userService.saveUser(user);
                usbFlashDriveInfoRetrievalFacade.changeUSBFlashDrive(user, ExistingRecordsOperationType.ENCRYPT);
                Notification.show("Successfully changed USB token!");
                UI.getCurrent().navigate("");
            }
        });
    }
}
