package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_UsersList extends PO_NavView {

	static public void search(WebDriver driver, String searchText) {
		WebElement email = driver.findElement(By.name("searchText"));
		email.click();
		email.clear();
		email.sendKeys(searchText);
		driver.findElement(By.id("search")).click();
	}

	static public void testNumberUsers(WebDriver driver, int expectedSize) {
		try {
			List<WebElement> elementos = checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
			assertTrue(elementos.size() == expectedSize);
		} catch(TimeoutException e) {
			if(expectedSize==0) {
				assertTrue(true);
			}
		}
	}

	public static void sendInvitationTo(WebDriver driver, String email) {
		search(driver, email);
		driver.findElement(By.id("sendInvitation")).click();
	}

	public static void checkInvitationAlreadySent(WebDriver driver, String email) {
		search(driver, email);
		assertNotNull(driver.findElement(By.id("invitationSent")));
	}

}
