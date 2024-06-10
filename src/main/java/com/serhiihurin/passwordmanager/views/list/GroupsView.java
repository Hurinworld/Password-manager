package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Group;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.GroupFacade;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

@PageTitle("Groups")
@Route(value = "groups", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class GroupsView extends Composite<VerticalLayout> {
    private final User currentAuthenticatedUser;
    private final GroupFacade groupFacade;
    private final RecordFacade recordFacade;
    private final ModelMapper modelMapper;

    Grid<Group> groupGrid = new Grid<>(Group.class);

    TextField textField = new TextField("Title");

    Button addGroupButton = new Button("Add");
    Button removeGroupButton = new Button("Remove");
    MultiSelectComboBox<RecordSimpleViewDTO> multiSelectComboBox = new MultiSelectComboBox<>("Records");

    H3 h3 = new H3("Add group");

    HorizontalLayout layoutRow = new HorizontalLayout();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    VerticalLayout layoutColumn3 = new VerticalLayout();

    public GroupsView(
            AuthenticationFacade authenticationFacade,
            GroupFacade groupFacade,
            RecordFacade recordFacade,
            ModelMapper modelMapper
    ) {
        this.currentAuthenticatedUser = authenticationFacade.getAuthenticatedUser();
        this.groupFacade = groupFacade;
        this.recordFacade = recordFacade;
        this.modelMapper = modelMapper;

        h3.setWidth("max-content");
        textField.setWidth("min-content");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        configureGrid();
        configureComboBox();
        configureButtons();
        configureLayouts();

        getContent().add(layoutRow);

        updateList();
    }

    private void configureLayouts() {
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, groupGrid);

        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, h3);
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, addGroupButton);
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, removeGroupButton);

        layoutRow.add(layoutColumn2);
        layoutColumn2.add(groupGrid);
        layoutRow.add(layoutColumn3);

        layoutColumn3.add(h3);
        layoutColumn3.add(textField);
        layoutColumn3.add(multiSelectComboBox);
        layoutColumn3.add(addGroupButton);
        layoutColumn3.add(removeGroupButton);
    }

    private void configureComboBox() {
        multiSelectComboBox.setWidth("min-content");
        multiSelectComboBox.setItems(
                recordFacade.getAllRecordsByUserId(currentAuthenticatedUser)
        );
        multiSelectComboBox.setItemLabelGenerator(RecordSimpleViewDTO::getTitle);
    }

    private void configureGrid() {
        groupGrid.addClassName("group-grid");
        groupGrid.setSizeFull();
        groupGrid.setColumns("title", "capacity");
        groupGrid.getColumns().forEach(column -> column.setAutoWidth(true));

        groupGrid.asSingleSelect().addValueChangeListener(event -> {
            textField.setValue(event.getValue().getTitle());
            List<RecordSimpleViewDTO> items = modelMapper.map(
                    event.getValue().getRecords(),
                    new TypeToken<List<RecordSimpleViewDTO>>(){}.getType()
            );
            multiSelectComboBox.setItems(items);
        });
    }

    private void configureButtons() {
        addGroupButton.setWidth("100%");
        addGroupButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addGroupButton.addClickListener(
                event -> createGroup()
        );
        removeGroupButton.setWidth("100%");

    }

    private void createGroup() {
        List<Record> recordList = multiSelectComboBox.getSelectedItems()
                .stream()
                .map(
                        item -> recordFacade
                                .getFullRecordInfoByUserId(currentAuthenticatedUser, item.getRecordId())
                )
                .toList();
        groupFacade.createGroup(
                Group.builder()
                        .title(textField.getValue())
                        .capacity(recordList.size())
                        .records(recordList)
                        .user(currentAuthenticatedUser)
                        .build()
        );
        updateList();
    }

    private void updateList() {
        groupGrid.setItems(
                groupFacade.getGroupsByUserId(currentAuthenticatedUser)
        );
    }
}
