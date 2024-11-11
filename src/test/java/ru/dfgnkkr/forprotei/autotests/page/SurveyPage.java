package ru.dfgnkkr.forprotei.autotests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Страница опроса/анкеты
 */
public class SurveyPage extends Page {

    public SurveyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "dataEmail")
    private WebElement emailInput;

    @FindBy(id = "dataName")
    private WebElement nameInput;

    @FindBy(id = "dataGender")
    private WebElement genderSelect;

    @FindBy(id = "dataCheck11")
    private WebElement checkOption11;

    @FindBy(id = "dataCheck12")
    private WebElement checkOption12;

    @FindBy(id = "dataSelect21")
    private WebElement radioOption21;

    @FindBy(id = "dataSelect22")
    private WebElement radioOption22;

    @FindBy(id = "dataSelect23")
    private WebElement radioOption23;

    @FindBy(id = "dataSend")
    private WebElement submitButton;

    @FindBy(id = "dataTable")
    private WebElement dataTable;

    public static final String OK_DIALOG_MESSAGE = "Данные добавлены.";
    private static final String OK_DIALOG_XPATH = "//div[@class='uk-modal-dialog']";
    private static final String OK_BUTTON_XPATH = OK_DIALOG_XPATH + "//button[text()='Ok']";

    @Override
    public boolean isPageDisplayed() {
        return isElementDisplayed(By.id("inputsPage"));
    }

    @Override
    public String getErrorMessage() {
        WebElement errorPage = findElementById("dataAlertsHolder");
        return errorPage.getText();
    }

    public boolean isOkDialogDisplayed() {
        return isElementDisplayed(By.xpath(OK_DIALOG_XPATH));
    }

    public void okButtonClick() {
        findElementByXPath(OK_BUTTON_XPATH).click();
    }

    public String getOkDialogMessage() {
        return findElementByXPath(OK_DIALOG_XPATH).findElement(By.className("uk-modal-content")).getText();
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void selectGender(String gender) {
        Select select = new Select(genderSelect);
        select.selectByVisibleText(gender);
    }

    public void checkOption1_1(boolean toCheck) {
        if (toCheck != checkOption11.isSelected()) {
            checkOption11.click();
        }
    }

    public void checkOption1_2(boolean toCheck) {
        if (toCheck != checkOption12.isSelected()) {
            checkOption12.click();
        }
    }

    public void selectRadioOption(int optionNumber) {
        switch (optionNumber) {
            case 1:
                radioOption21.click();
                break;
            case 2:
                radioOption22.click();
                break;
            case 3:
                radioOption23.click();
                break;
        }
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public boolean isDataTableDisplayed() {
        return dataTable.isDisplayed();
    }

    public String getDataTableRow(int rowNum) {
        List<WebElement> rows = dataTable.findElements(By.xpath("./tbody/tr"));
        if (rows != null && rows.size() - 1 >= rowNum) {
            return rows.get(rowNum).getText();
        }
        return "";
    }
}
