package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_RegisterUser;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MySocialNetworkTests {
	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\xxerp\\OneDrive\\Escritorio\\Proyecto Manhatan\\Grado\\3º\\SDI\\work\\MySocialNetwork\\lib\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}
	
	
	//###################################################//
	//	1. Registrarse como usuario						 //
	//###################################################//

	
	// PR01. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "example@example.com", "Adrian", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Lista Usuarios");
	}
	
	// PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos)
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "", "", "", "123456", "123456");
		PO_View.checkElement(driver, "text", "Registrarse");
	}
	
	// PR03. Registro de Usuario con datos inválidos (repetición de contraseña inválida)
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "example2@example.com", "Adrian", "Perez", "123456", "1234567");
		PO_View.checkElement(driver, "text", "Contraseña repetida");
	}
	
	// PR04. Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "admin@email.com", "Adrian", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Existe otro usuario con este email");
	}
	
	//###################################################//
	//	2. Iniciar sesión					 			 //
	//###################################################//
	
	
	// PR05. Inicio de sesión con datos válidos (administrador)
	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_View.checkElement(driver, "text", "Lista Usuarios");
	}
	
	// PR06. Inicio de sesión con datos válidos (usuario estándar)
	@Test
	public void PR06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user@email.com", "user");
		PO_View.checkElement(driver, "text", "Lista Usuarios");
	}
	
	// PR07. Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos)
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		PO_View.checkElement(driver, "text", "Entrar");
	}
	
	// PR08. Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña incorrecta)
	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user@email.com", "uesr");
		PO_View.checkElement(driver, "text", "Entrar");
	}
	
	//###################################################//
	//	3. Fin de sesión					 			 //
	//###################################################//
		
	
	// PR09. Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión (Login)
	@Test
	public void PR09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");
	}
	
	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado
	@Test
	public void PR10() {
		By boton = By.id("logout");
		try{
			driver.findElement(boton); 
		}catch(NoSuchElementException e){
			//Significa que no existe la opción de desconectarse
		}
	}
	
	//###################################################//
	//	4. Listado de usuarios				 			 //
	//###################################################//
	
	
	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
	@Test
	public void PR11() {
		//Como admin salen el usuario estándar del InsertSampleService y el añadido en el test PR01
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elementos.size() == 2);
		
		//Nos desconectamos y entramos como usuario estándar
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");
		PO_LoginView.fillForm(driver, "user@email.com", "user");
		
		//Como usuario estándar solo se mostraría el usuario del test PR01
		elementos = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elementos.size() == 1);
	}
	
}
