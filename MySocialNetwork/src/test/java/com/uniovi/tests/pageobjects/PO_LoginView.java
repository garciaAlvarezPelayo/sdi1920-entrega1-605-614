package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView{
	static public void login(WebDriver driver, String username, String password) {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, username, password);
	}
	
	static public void fillForm(WebDriver driver, String emailp, String passwordp) {
		WebElement email = driver.findElement(By.name("username"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
//Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
