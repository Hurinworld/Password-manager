package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.entity.Group;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
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

import java.util.List;

public class RecordForm extends FormLayout {
    Binder<RecordExtendedViewDTO> binder = new BeanValidationBinder<>(RecordExtendedViewDTO.class);

    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    PasswordField username = new PasswordField("Username");
    PasswordField password = new PasswordField("Password");
    TextField url = new TextField("URL");
    ComboBox<Group> group = new ComboBox<>("Group");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public RecordForm(List<Group> groups) {

        addClassName("record-form");
        binder.bindInstanceFields(this);

        group.setItems(groups);
        group.setItemLabelGenerator(Group::getTitle);

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
        binder.setBean(recordExtendedViewDTO);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> fireEvent(new SaveEvent(this, binder.getBean())));
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    public static abstract class ContactFormEvent extends ComponentEvent<RecordForm> {
        private final RecordExtendedViewDTO recordExtendedViewDTO;

        protected ContactFormEvent(RecordForm source, RecordExtendedViewDTO recordExtendedViewDTO) {
            super(source, false);
            this.recordExtendedViewDTO = recordExtendedViewDTO;
        }

        public RecordExtendedViewDTO getRecord() {
            return recordExtendedViewDTO;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(RecordForm source, RecordExtendedViewDTO recordExtendedViewDTO) {
            super(source, recordExtendedViewDTO);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(RecordForm source, RecordExtendedViewDTO recordExtendedViewDTO) {
            super(source, recordExtendedViewDTO);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(RecordForm source) {
            super(source, null);
        }
    }

    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        addListener(DeleteEvent.class, listener);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }
    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }
}
