package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

public class PO_FriendsList extends PO_NavView{

	public static void testFriend(WebDriver driver, String email) {
		checkElement(driver, "text", email);
		assertTrue(true);
	}
	
	

}
