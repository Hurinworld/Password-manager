package com.serhiihurin.passwordmanager;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@SpringBootApplication
@PWA(name = "Password Manager", shortName = "Password Manager")
public class PasswordManagerApplication implements AppShellConfigurator {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		SpringApplication.run(PasswordManagerApplication.class, args);
	}

}
