package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;

import java.util.Collections;

@PageTitle("Password Manager")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
    private final AuthenticationFacade authenticationFacade;
    private final RecordFacade recordFacade;
//    private final UserService userService;
    private final ModelMapper modelMapper;
    private RecordForm recordForm;
    private final User currentAuthenticatedUser;

    Grid<RecordSimpleViewDTO> grid = new Grid<>(RecordSimpleViewDTO.class);
    TextField searchField = new TextField();

    public ListView(AuthenticationFacade authenticationFacade, RecordFacade recordFacade, ModelMapper modelMapper) {
        this.authenticationFacade = authenticationFacade;
        this.recordFacade = recordFacade;
//        this.userService = userService;
        this.modelMapper = modelMapper;

        currentAuthenticatedUser = authenticationFacade.getAuthenticatedUser();

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    private void configureGrid() {
        grid.addClassName("record-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "url", "group");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
//        User user = authenticationFacade.getAuthenticatedUser();

        grid.asSingleSelect().addValueChangeListener(event -> editRecord(event.getValue()));
    }

    private void editRecord(RecordSimpleViewDTO recordSimpleViewDTO) {
        recordForm.setRecord(recordFacade.getExtendedRecordByUserId(authenticationFacade.getAuthenticatedUser(), recordSimpleViewDTO.getRecordId()));
    }

    private Component getToolbar() {
        searchField.setPlaceholder("Search record by name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> updateList());

        Button addRecordButton = new Button("Add record");

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
        recordForm = new RecordForm(Collections.emptyList(), modelMapper);
        recordForm.setWidth("25em");
    }

    private void updateList() {
        grid.setItems(recordFacade.filterRecordsByTitle(currentAuthenticatedUser.getUserId(), searchField.getValue()));
    }
}
