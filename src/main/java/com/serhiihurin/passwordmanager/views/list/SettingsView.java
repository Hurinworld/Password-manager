package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@PermitAll
public class SettingsView extends Composite<VerticalLayout> {
    private final UserService userService;
    private final User currentAuthenticatedUser;

    private final H4 h4 = new H4("Basic");
    private final Hr hr = new Hr();
    private final Hr hr2 = new Hr();
    private final H4 h42 = new H4("Change email");
    private final Hr hr3 = new Hr();
    private final Hr hr4 = new Hr();
    private final H4 h43 = new H4("Change token");
    private final Hr hr5 = new Hr();

    private final TextField firstNameField = new TextField("First name");
    private final TextField lastNameField = new TextField("Last name");
    private final EmailField emailField = new EmailField("Email");

    private final Button saveButton = new Button("Save");
    private final Button sendEmailVerificationButton = new Button("Send verification");
    private final Button unlinkUsbToken = new Button("Unlink USB Token");

    HorizontalLayout layoutRow = new HorizontalLayout();
    HorizontalLayout layoutRow2 = new HorizontalLayout();

    public SettingsView(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;

        currentAuthenticatedUser = authenticationFacade.getAuthenticatedUser();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h4);
        h4.setWidth("max-content");
        configureButtons();
        firstNameField.setWidth("min-content");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, lastNameField);
        lastNameField.setWidth("min-content");
        layoutRow.setAlignSelf(FlexComponent.Alignment.END, saveButton);

        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h42);
        h42.setWidth("max-content");

        emailField.setWidth("min-content");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.END, sendEmailVerificationButton);

        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h43);
        h43.setWidth("max-content");

        configureLayout(layoutRow);
        configureLayout(layoutRow2);

        getContent().add(h4);
        getContent().add(hr);
        getContent().add(layoutRow);

        layoutRow.add(firstNameField);
        layoutRow.add(lastNameField);
        layoutRow.add(saveButton);

        getContent().add(hr2);
        getContent().add(h42);
        getContent().add(hr3);
        getContent().add(layoutRow2);

        layoutRow2.add(emailField);
        layoutRow2.add(sendEmailVerificationButton);

        getContent().add(hr4);
        getContent().add(h43);
        getContent().add(hr5);
        getContent().add(unlinkUsbToken);
    }

    private void configureButtons() {
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            validateAndSetUserFields();
            userService.saveUser(currentAuthenticatedUser);
        });

        sendEmailVerificationButton.setWidth("min-content");
        sendEmailVerificationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        unlinkUsbToken.setText("Unlink USB Token");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, unlinkUsbToken);
        unlinkUsbToken.setWidth("min-content");
        unlinkUsbToken.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        unlinkUsbToken.addClickListener(event -> UI.getCurrent().navigate("link-new-token"));
    }

    private void configureLayout(HorizontalLayout layout) {
        layout.setWidthFull();
        getContent().setFlexGrow(1.0, layout);
        layout.addClassName(Gap.MEDIUM);
        layout.setWidth("100%");
        layout.setHeight("min-content");
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
    }
    private void validateAndSetUserFields() {
        if (!firstNameField.getValue().isBlank()) {
            currentAuthenticatedUser.setFirstName(firstNameField.getValue());
        }
        if (!lastNameField.getValue().isBlank()) {
            currentAuthenticatedUser.setLastName(lastNameField.getValue());
        }
    }
}
