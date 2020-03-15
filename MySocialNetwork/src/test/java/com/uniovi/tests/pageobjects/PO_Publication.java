package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Publication extends PO_NavView {
	static public void fillForm(WebDriver driver, String titlep, String textp) {
		fillStandardForm(driver, titlep, textp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void fillForm(WebDriver driver, String titlep, String textp, String rutaImagenp) {
		fillStandardForm(driver, titlep, textp);
		WebElement image = driver.findElement(By.name("image"));
		image.click();
		image.clear();
		image.sendKeys(rutaImagenp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	static private void fillStandardForm(WebDriver driver, String titlep, String textp) {
		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titlep);
		WebElement text = driver.findElement(By.name("text"));
		text.click();
		text.clear();
		text.sendKeys(textp);
	}
}
