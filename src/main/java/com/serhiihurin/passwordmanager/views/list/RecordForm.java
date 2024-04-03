package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Group;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.modelmapper.ModelMapper;

import java.util.List;

public class RecordForm extends FormLayout {
    Binder<RecordExtendedViewDTO> binder = new BeanValidationBinder<>(RecordExtendedViewDTO.class);

    private final ModelMapper modelMapper;

    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    PasswordField username = new PasswordField("Username");
    PasswordField password = new PasswordField("Password");
    TextField url = new TextField("URL");
    ComboBox<Group> group = new ComboBox<>("Group");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private RecordExtendedViewDTO recordExtendedViewDTO;

    public RecordForm(List<Group> groups, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        addClassName("record-form");
        binder.bindInstanceFields(this);

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

    public void setRecord(RecordExtendedViewDTO recordExtendedViewDTO) {
        this.recordExtendedViewDTO = recordExtendedViewDTO;
        binder.readBean(recordExtendedViewDTO);
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
