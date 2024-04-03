package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final AuthenticationFacade authenticationFacade;

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("email");
    private final PasswordField masterPassword = new PasswordField("master password");
    private final PasswordField confirmMasterPassword = new PasswordField("confirm master password");

    Button register = new Button("Register");

    public RegisterView(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                new H1("Register | Password Manager"),
                firstName,
                lastName,
                email,
                masterPassword,
                confirmMasterPassword,
                configureRegisterButton()
        );
    }

    private Component configureRegisterButton() {
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        register.addClickShortcut(Key.ENTER);

        register.addClickListener(event -> {
            authenticationFacade.registerUser(
                    firstName.getValue(),
                    lastName.getValue(),
                    email.getValue(),
                    masterPassword.getValue(),
                    confirmMasterPassword.getValue()
            );
            UI.getCurrent().navigate("home");
        });

        return register;
    }
}
