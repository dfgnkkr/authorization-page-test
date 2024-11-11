package ru.dfgnkkr.forprotei.autotests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Страница авторизации
 */
public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "loginEmail")
    private WebElement emailInput;

    @FindBy(id = "loginPassword")
    private WebElement passwordInput;

    @FindBy(id = "authButton")
    private WebElement loginButton;

    public static final String INVALID_LOGIN_MESSAGE = "Неверный E-Mail или пароль";
    public static final String INVALID_EMAIL_FORMAT_MESSAGE = "Неверный формат E-Mail";

    @Override
    public boolean isPageDisplayed() {
        return isElementDisplayed(By.id("authPage"));
    }

    @Override
    public String getErrorMessage() {
        WebElement errorPage = findElementById("authAlertsHolder");
        return errorPage.getText();
    }

    public void loginWithCredentials(String login, String password) {
        enterLogin(login);
        enterPassword(password);
        clickLoginButton();
    }

    public void enterLogin(String login) {
        emailInput.clear();
        emailInput.sendKeys(login);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public boolean isLoginInputDisplayed() {
        return isElementDisplayed(emailInput);
    }

    public boolean isPasswordInputDisplayed() {
        return isElementDisplayed(passwordInput);
    }
}
