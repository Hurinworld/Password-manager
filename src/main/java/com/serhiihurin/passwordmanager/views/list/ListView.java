package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
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

import java.util.Collections;
import java.util.List;

@PageTitle("Password Manager")
@Route(value = "")
public class ListView extends VerticalLayout {
    private final RecordFacade recordFacade;
    private final UserService userService;
    private RecordForm recordForm;

    Grid<RecordSimpleViewDTO> grid = new Grid<>(RecordSimpleViewDTO.class);
    TextField searchField = new TextField();

    public ListView(RecordFacade recordFacade, UserService userService) {
        this.recordFacade = recordFacade;
        this.userService = userService;

        addClassName("list-view");
        setSizeFull();
//        add(new H1("Hello World!"));
        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );
    }

    private void configureGrid() {
        grid.addClassName("record-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "url", "group");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.setItems(recordFacade.getRecordByUserId(userService.getUser(1459912354L), 9876543210L));
    }

    private Component getToolbar() {
        searchField.setPlaceholder("Search record by name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

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
        recordForm = new RecordForm(Collections.emptyList());
        recordForm.setWidth("25em");
    }
}
