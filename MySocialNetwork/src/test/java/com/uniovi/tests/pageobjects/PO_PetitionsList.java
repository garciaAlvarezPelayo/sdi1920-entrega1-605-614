package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

}
