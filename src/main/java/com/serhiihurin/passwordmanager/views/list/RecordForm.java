package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.entity.Group;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class RecordForm extends FormLayout {
    TextField title = new TextField("Title");
    TextField description = new TextField("Decsription");
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");
    TextField url = new TextField("URL");
    ComboBox<Group> group = new ComboBox<>("Group");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public RecordForm(List<Group> groups) {
        addClassName("record-form");

        group.setItems(groups);
        group.setItemLabelGenerator(Group::getGroupName);

        add(
                title,
                description,
                username,
                password,
                url,
                group,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }
}
