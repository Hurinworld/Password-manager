package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.GroupFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Password Manager")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Slf4j
public class ListView extends VerticalLayout {
    private final RecordFacade recordFacade;
    private final GroupFacade groupFacade;
    private RecordForm recordForm;
    private final User currentAuthenticatedUser;

    Grid<RecordSimpleViewDTO> grid = new Grid<>(RecordSimpleViewDTO.class);
    TextField searchField = new TextField();

    public ListView(
            AuthenticationFacade authenticationFacade,
            RecordFacade recordFacade,
            GroupFacade groupFacade
    ) {
        this.recordFacade = recordFacade;
        this.groupFacade = groupFacade;

        currentAuthenticatedUser = authenticationFacade.getAuthenticatedUser();
        log.info("Authenticated user in ListView: {}", currentAuthenticatedUser);
        log.info(currentAuthenticatedUser.getPassword());

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeRecordForm();
    }


    private void configureGrid() {
        grid.addClassName("record-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "url", "group");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editRecord(event.getValue()));
    }

    private void editRecord(RecordSimpleViewDTO recordSimpleViewDTO) {
        if (recordSimpleViewDTO == null) {
            closeRecordForm();
        } else {
            recordForm.setRecord(
                    recordFacade.getExtendedRecordByUserId(
                            currentAuthenticatedUser, recordSimpleViewDTO.getRecordId()
                    )
            );
            recordForm.setVisible(true);
            recordForm.addClassName("record-form");
        }
    }

    private Component getToolbar() {
        searchField.setPlaceholder("Search record by name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> updateList());

        Button addRecordButton = new Button("Add record");
        addRecordButton.addClickListener(event -> addRecord());

        HorizontalLayout toolbar = new HorizontalLayout(searchField, addRecordButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, recordForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, recordForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        recordForm = new RecordForm(groupFacade.getGroupsByUserId(currentAuthenticatedUser));
        recordForm.setWidth("25em");

        recordForm.addSaveListener(this::createRecord);
        recordForm.addDeleteListener(this::deleteRecord);
        recordForm.addCloseListener(event -> closeRecordForm());
    }

    private void updateList() {
        grid.setItems(
                recordFacade.filterRecordsByTitle(
                        currentAuthenticatedUser.getUserId(), searchField.getValue()
                )
        );
    }

    private void closeRecordForm() {
        recordForm.setRecord(null);
        recordForm.setVisible(false);
        recordForm.removeClassName("record-form");
    }

    private void addRecord() {
        grid.asSingleSelect().clear();
        recordForm.setRecord(new RecordExtendedViewDTO());
        recordForm.setVisible(true);
        recordForm.addClassName("record-form");
    }

    private void createRecord(RecordForm.SaveEvent event) {
        recordFacade.createRecord(currentAuthenticatedUser, event.getRecord());
        updateList();
        closeRecordForm();
    }

    private void deleteRecord(RecordForm.DeleteEvent event) {
        recordFacade.deleteRecord(currentAuthenticatedUser, event.getRecord().getRecordId());
        updateList();
        closeRecordForm();
    }
}
