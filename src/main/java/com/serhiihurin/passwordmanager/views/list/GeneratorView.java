package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Generate password | Password Manager")
@Route(value = "generator", layout = MainLayout.class)
@PermitAll
public class GeneratorView extends VerticalLayout {
    private final GeneratorService generatorService;
    private final TextField passwordField = new TextField("Your password is:");
    private final TextField lengthField = new TextField("Length:");
    private final Checkbox includeUpperCase = new Checkbox("Include upper case (A-Z):");
    private final Checkbox includeNumbers = new Checkbox("Include numbers (0-9):");
    private final Checkbox includeSpecialCharacters = new Checkbox("Include special characters (*, $, \\):");

    private final Button generateButton = new Button("Generate");

    public GeneratorView(GeneratorService generatorService) {
        this.generatorService = generatorService;

        configureFields();
        configureGenerateButton();
        configureCheckBox();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(passwordField, includeUpperCase, includeNumbers, includeSpecialCharacters, lengthField, generateButton);

        fillPasswordField();
    }

    private void fillPasswordField() {
        passwordField.setValue(
                generatorService.generatePassword(
                        lengthField.getValue(),
                        includeUpperCase.getValue(),
                        includeNumbers.getValue(),
                        includeSpecialCharacters.getValue()
                )
        );
    }

    private void configureCheckBox() {
        includeUpperCase.setValue(true);
        includeNumbers.setValue(true);
        includeSpecialCharacters.setValue(true);
    }

    private void configureFields() {
        passwordField.setWidth("300px");
        passwordField.setClearButtonVisible(true);
        lengthField.setWidth("100px");
        lengthField.setClearButtonVisible(true);
        lengthField.setMinLength(1);
        lengthField.setMaxLength(3);
        lengthField.setValue("15");
    }

    private void configureGenerateButton() {
        generateButton.addClickListener(event -> fillPasswordField());
    }
}
