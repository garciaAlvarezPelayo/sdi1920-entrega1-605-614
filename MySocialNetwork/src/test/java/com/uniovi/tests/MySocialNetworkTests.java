package com.uniovi.tests;

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
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_PetitionsList;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_FriendsList;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_RegisterUser;
import com.uniovi.tests.pageobjects.PO_UsersList;
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

	// ###################################################//
	// 1. Registrarse como usuario //
	// ###################################################//

	// PR01. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "example@example.com", "Adrian", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Lista Usuarios");
	}

	// PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos)
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterUser.fillForm(driver, "", "", "", "123456", "123456");
		PO_View.checkElement(driver, "text", "Registrarse");
	}

	// PR03. Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida)
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

	// ###################################################//
	// 2. Iniciar sesión //
	// ###################################################//

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

	// PR07. Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos)
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		PO_View.checkElement(driver, "text", "Entrar");
	}

	// PR08. Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta)
	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user@email.com", "uesr");
		PO_View.checkElement(driver, "text", "Entrar");
	}

	// ###################################################//
	// 3. Fin de sesión //
	// ###################################################//

	// PR09. Hacer click en la opción de salir de sesión y comprobar que se redirige
	// a la página de inicio de sesión (Login)
	@Test
	public void PR09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado
	@Test
	public void PR10() {
		By boton = By.id("logout");
		try {
			driver.findElement(boton);
		} catch (NoSuchElementException e) {
			// Significa que no existe la opción de desconectarse
		}
	}

	// ###################################################//
	// 4. Listado de usuarios //
	// ###################################################//

	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema
	@Test
	public void PR11() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.testNumberUsers(driver, 2);

		// Nos desconectamos y entramos como usuario estándar
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");
		PO_LoginView.fillForm(driver, "user@email.com", "user");

		// Como usuario estándar solo se mostraría el usuario del test PR01
		PO_UsersList.testNumberUsers(driver, 1);
	}

	// ###################################################//
	// 5. Buscar usuarios //
	// ###################################################//

	// PR12. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que corresponde con el listado usuarios existentes en el sistema.
	@Test
	public void PR12() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.search(driver, "");
		PO_UsersList.testNumberUsers(driver, 2);
	}

	// PR13. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se
	// muestra la página que corresponde, con la lista de usuarios vacía.
	@Test
	public void PR13() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.search(driver, "ñ");
		PO_UsersList.testNumberUsers(driver, 0);
	}

	// PR14. Hacer una búsqueda con un texto específico y comprobar que se muestra
	// la página que
	// corresponde, con la lista de usuarios en los que el texto especificados sea
	// parte de su nombre, apellidos o
	// de su email.
	@Test
	public void PR14() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.search(driver, "user@email.com");
		PO_UsersList.testNumberUsers(driver, 1);
	}

	// ###################################################//
	// 6. Enviar invitación de amistad //
	// ###################################################//

	// PR15. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones
	// (punto siguiente).
	@Test
	public void PR15() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.sendInvitationTo(driver, "user@email.com");
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");
		PO_LoginView.login(driver, "user@email.com", "user");
		PO_NavView.clickOptionById(driver, "FriendsMenu");
		PO_NavView.clickOptionById(driver, "petitionList");
		PO_PetitionsList.testInvitationBy(driver, "admin@email.com");
	}

	// PR16. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos
	// enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada
	// previamente.
	@Test
	public void PR16() {
		// Como admin salen el usuario estándar del InsertSampleService y el añadido en
		// el test PR01
		PO_LoginView.login(driver, "admin@email.com", "admin");
		PO_UsersList.checkInvitationAlreadySent(driver, "user@email.com");
	}

	// ###################################################//
	// 7. Listar las invitaciones de amistad recibidas //
	// ###################################################//

	// PR17. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con
	// un listado que
	// contenga varias invitaciones recibidas.
	@Test
	public void PR17() {
		PO_LoginView.login(driver, "example@example.com", "123456");
		PO_UsersList.sendInvitationTo(driver, "user@email.com");
		PO_NavView.clickOption(driver, "logout", "id", "texto-entrar");

		PO_LoginView.login(driver, "user@email.com", "user");
		PO_NavView.clickOptionById(driver, "FriendsMenu");
		PO_NavView.clickOptionById(driver, "petitionList");
		PO_PetitionsList.testNumberInvitations(driver, 2);
	}

	// ###################################################//
	// 8. Aceptar una invitación //
	// ###################################################//

	// PR18. Sobre el listado de invitaciones recibidas. Hacer click en el
	// botón/enlace de una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones.

	@Test
	public void PR18() {
		PO_LoginView.login(driver, "user@email.com", "user");
		PO_NavView.clickOptionById(driver, "FriendsMenu");
		PO_NavView.clickOptionById(driver, "petitionList");
		PO_PetitionsList.testNumberInvitations(driver, 2);
		PO_PetitionsList.acceptInvitationFrom(driver, "admin@email.com");
		PO_PetitionsList.testNumberInvitations(driver, 1);
	}

	// ###################################################//
	// 9. Listado de amigos //
	// ###################################################//

	// PR19. Mostrar el listado de amigos de un usuario. Comprobar que el listado
	// contiene los amigos
	// que deben ser.

	@Test
	public void PR19() {
		PO_LoginView.login(driver, "user@email.com", "user");
		PO_NavView.clickOptionById(driver, "FriendsMenu");
		PO_NavView.clickOptionById(driver, "friendList");
		PO_FriendsList.testFriend(driver, "admin@email.com");
	}

	// ###################################################//
	// 10. Internacionalización de todas las vistas //
	// ###################################################//

	// PR20. Visualizar al menos cuatro páginas en Español/Inglés/Español
	// (comprobando que algunas
	// de las etiquetas cambian al idioma correspondiente). Ejemplo, Página
	// principal/Opciones Principales de
	// Usuario/Listado de Usuarios.

	@Test
	public void PR20() {
		PO_HomeView.checkChangeIdiom(driver, "Spanish", "English", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		PO_NavView.clickLoginOption(driver);
		PO_LoginView.checkChangeIdiom(driver, "Spanish", "English", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		PO_LoginView.login(driver, "user@email.com", "user");
		PO_UsersList.checkChangeIdiom(driver, "Spanish", "English", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		PO_NavView.clickOptionById(driver, "FriendsMenu");
		PO_NavView.clickOptionById(driver, "friendList");
		PO_FriendsList.checkChangeIdiom(driver, "Spanish", "English", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
	}

	// ###################################################//
	// 11. Internacionalización de todas las vistas //
	// ###################################################//

	// PR21. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al
	// formulario de login.

	@Test
	public void PR21() {
		driver.navigate().to(URL + "/users/list");
		PO_LoginView.checkPasswordMessage(driver, PO_Properties.getSPANISH());
	}

	// PR22. Intentar acceder sin estar autenticado a la opción de listado de
	// publicaciones de un usuario
	// estándar. Se deberá volver al formulario de login.

	@Test
	public void PR22() {
		driver.navigate().to(URL + "/publication/list");
		PO_LoginView.checkPasswordMessage(driver, PO_Properties.getSPANISH());
	}

	// PR23. Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo
	// para usuarios administradores (Se puede añadir una opción cualquiera en el
	// menú). Se deberá indicar un
	// mensaje de acción prohibida.

	@Test
	public void PR23() {
		PO_LoginView.login(driver, "user@email.com", "user");
		driver.navigate().to(URL + "/closeWeb");
		PO_View.checkElement(driver, "text", "You do not have access permissions, Go out");
	}
}
