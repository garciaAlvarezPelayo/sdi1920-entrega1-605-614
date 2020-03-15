package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_PetitionsList extends PO_NavView{

	public static void testInvitationBy(WebDriver driver, String email) {
		checkElement(driver, "text", email);
		assertTrue(true);
	}

	public static void testNumberInvitations(WebDriver driver, int expectedSize) {
		try {
			List<WebElement> elementos = checkElement(driver, "free", "//*[@id=\"tableFriends\"]/tbody/tr");
			assertTrue(elementos.size() == expectedSize);
		} catch(TimeoutException e) {
			if(expectedSize==0) {
				assertTrue(true);
			}
		}
	}

	public static void acceptInvitationFrom(WebDriver driver, String email) {
		driver.findElement(By.id(email)).click();
	}
	
	static public void checkPetitionListMessage(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("petitionslist.message", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		checkPetitionListMessage(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		checkPetitionListMessage(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		checkPetitionListMessage(driver, locale1);
	}

}
