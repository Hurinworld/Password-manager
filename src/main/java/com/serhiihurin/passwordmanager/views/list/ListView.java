package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Password Manager")
@Route(value = "")
public class ListView extends VerticalLayout {
    Grid<RecordSimpleViewDTO> grid = new Grid<>(RecordSimpleViewDTO.class);
    TextField searchField = new TextField();

    public ListView() {
        addClassName("list-view");
        setSizeFull();
//        add(new H1("Hello World!"));
        confidureGrid();
    }

    private void confidureGrid() {
        grid.addClassName("record-grid");
        grid.setSizeFull();
        grid.setColumns("Title", "Description", "URL", "Group");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

}
