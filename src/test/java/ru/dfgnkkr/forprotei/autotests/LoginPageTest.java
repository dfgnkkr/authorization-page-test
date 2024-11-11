package ru.dfgnkkr.forprotei.autotests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.dfgnkkr.forprotei.autotests.page.LoginPage;
import ru.dfgnkkr.forprotei.autotests.page.SurveyPage;

import java.time.Duration;

import static org.junit.Assert.*;

/**
 * Тесты страницы авторизации и анкеты
 */
public class LoginPageTest {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static SurveyPage surveyPage;
    private static final String CORRECT_LOGIN = "test@protei.ru";
    private static final String CORRECT_PASSWORD = "test";

    @BeforeClass
    public static void setup() {
        // создание драйвера
        // System.setProperty("webdriver.gecko.driver", "C:\geckodriver.exe");
        driver = new FirefoxDriver();
        // в нашем случае не потребуется, но в реальной системе действия могут выполняться не быстро
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Before
    public void openPage() {
        // создание страниц
        loginPage = new LoginPage(driver);
        surveyPage = new SurveyPage(driver);
        // в нашем случае все тесты начинаются с открытия loginPage
        loginPage.open();
    }

    /**
     * Проверяем открытие страницы. Форма анкеты не должна отображаться.
     */
    @Test
    public void shouldOpenLoginPage() {
        assertTrue("Страница входа не отображается.", loginPage.isPageDisplayed());
        assertFalse("Страница (или форма?) анкеты отображается до авторизации.", surveyPage.isPageDisplayed());
    }

    /**
     * Проверяем контент на странице авторизации.
     */
    @Test
    public void checkContentOnLoginPage() {
        assertTrue("Страница входа не отображается.", loginPage.isPageDisplayed());
        assertTrue("Ошибок при открытии страницы быть не должно.", loginPage.getErrorMessage().isEmpty());
        assertTrue("Поле ввода email не отображается", loginPage.isLoginInputDisplayed());
        assertTrue("Поле ввода пароля не отображается", loginPage.isPasswordInputDisplayed());
        assertTrue("Кнопка \"Вход\" не отображается", loginPage.isLoginButtonDisplayed());
    }

    /**
     * Проверяем успешную авторизацию.
     */
    @Test
    public void shouldLogin() {
        loginPage.enterLogin(CORRECT_LOGIN);
        loginPage.enterPassword(CORRECT_PASSWORD);
        loginPage.clickLoginButton();
        assertFalse("Страница авторизации отображается после успешно авторизации.", loginPage.isPageDisplayed());
        assertTrue("Страница анкеты не отображается после успешно авторизации.", surveyPage.isPageDisplayed());
    }

    /**
     * Проверяем ошибку при неверном вводе email
     */
    @Test
    public void canNotLoginWithInvalidEmail() {
        loginPage.loginWithCredentials("wrong_email@email.com", CORRECT_PASSWORD);
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, LoginPage.INVALID_LOGIN_MESSAGE);
    }

    /**
     * Проверяем ошибку при неверном вводе пароля
     */
    @Test
    public void canNotLoginWithInvalidPassword() {
        loginPage.loginWithCredentials(CORRECT_LOGIN, "wrong_password_test");
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, LoginPage.INVALID_LOGIN_MESSAGE);
    }

    /**
     * Проверяем ошибку при неверном формате email
     */
    @Test
    public void canNotLoginWithIncorrectFormatEmail() {
        loginPage.loginWithCredentials("wrong_format_email", CORRECT_PASSWORD);
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, LoginPage.INVALID_EMAIL_FORMAT_MESSAGE);
    }

    /**
     * Проверяем ошибку при пустом поле ввода email
     */
    @Test
    public void canNotLoginWithoutEmail() {
        loginPage.loginWithCredentials("", CORRECT_PASSWORD);
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, LoginPage.INVALID_EMAIL_FORMAT_MESSAGE);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}