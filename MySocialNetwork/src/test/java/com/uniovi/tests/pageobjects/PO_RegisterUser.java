package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegisterUser extends PO_NavView{
	static public void register(WebDriver driver, String email, String name, String surname, String password) {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		fillForm(driver, email, name, surname, password, password);
	}
	
	static public void fillForm(WebDriver driver, String emailp, String nombrep, String apellidosp, String passwordp,
			String repetirPasswordp) {
		WebElement email = driver.findElement(By.name("email"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement nombre = driver.findElement(By.name("name"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(nombrep);
		WebElement apellidos = driver.findElement(By.name("surname"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(apellidosp);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		WebElement repetirPassword = driver.findElement(By.name("passwordConfirm"));
		repetirPassword.click();
		repetirPassword.clear();
		repetirPassword.sendKeys(repetirPasswordp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	public static void registerUser(WebDriver driver, String email, String name, String surname, String password) {
		PO_LoginView.login(driver, "admin@email.com", "admin");
		register(driver, email, name, surname, password);
	}
}
