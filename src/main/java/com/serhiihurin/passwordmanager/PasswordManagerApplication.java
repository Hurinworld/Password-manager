package com.serhiihurin.passwordmanager;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("flowcrmtutorial")
@PWA(name = "Password Manager", shortName = "Password Manager")
@NpmPackage(value = "line-awesome", version = "2.0.0")
public class PasswordManagerApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(PasswordManagerApplication.class, args);
	}

}
