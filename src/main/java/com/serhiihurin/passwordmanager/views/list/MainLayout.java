package com.serhiihurin.passwordmanager.views.list;

import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private final AuthenticationFacade authenticationFacade;
    public MainLayout(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
       H1 logo = new H1("Password Manager");
       logo.addClassNames("text-l", "m-m");

       String username = authenticationFacade.getAuthenticatedUser().getUsername();
       Button logout = new Button("Log out " + username, event -> authenticationFacade.logout());

       HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
       header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
       header.expand(logo);
       header.setWidthFull();
       header.addClassNames("py-0", "px-m");

       addToNavbar(header);
    }

    private void createDrawer() {
        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Home", "/home", VaadinIcon.HOME.create()),
                new SideNavItem("Generator", "/generator", VaadinIcon.KEY.create()),
                new SideNavItem("Groups", "/groups", VaadinIcon.FOLDER.create()),
                new SideNavItem("Settings", "/settings", VaadinIcon.COG.create())
        );
        return sideNav;
    }
}
