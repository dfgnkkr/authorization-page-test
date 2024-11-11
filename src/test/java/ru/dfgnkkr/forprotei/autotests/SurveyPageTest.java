package ru.dfgnkkr.forprotei.autotests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.dfgnkkr.forprotei.autotests.data.SurveyData;
import ru.dfgnkkr.forprotei.autotests.page.LoginPage;
import ru.dfgnkkr.forprotei.autotests.page.SurveyPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SurveyPageTest {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static SurveyPage surveyPage;
    private static final String CORRECT_LOGIN = "test@protei.ru";
    private static final String CORRECT_PASSWORD = "test";
    private final SurveyData surveyData;
    // так как не все тесты завершатся успешным добавлением строки в таблицу результатов - приходится вводить счетчик
    private static int resultTableRowsCount;

    public SurveyPageTest(SurveyData surveyData) {
        this.surveyData = surveyData;
    }

    @BeforeClass
    public static void setup() {
        // создание драйвера
        // System.setProperty("webdriver.gecko.driver", "C:\geckodriver.exe");
        driver = new FirefoxDriver();
        // в нашем случае не потребуется, но в реальной системе действия могут выполняться не быстро
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // создание страниц
        loginPage = new LoginPage(driver);
        surveyPage = new SurveyPage(driver);
        // в нашем случае все тесты начинаются с открытия loginPage
        loginPage.open();
        // сразу авторизуемся - страницу логина мы уже протестировали в LoginPageTest
        openSurveyPage();
        // создаем счетчик
        resultTableRowsCount = 0;
    }

    /**
     * Открытие формы анкеты (по факту это просто ввод логина/пароля)
     */
    private static void openSurveyPage() {
        loginPage.loginWithCredentials(CORRECT_LOGIN, CORRECT_PASSWORD);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<SurveyData> users = Arrays.asList(
                mapper.readValue(new File("./src/test/resources/survey_test_data.json"), SurveyData[].class));

        // Преобразуем список пользователей в массив объектов для JUnit
        return users.stream()
                .map(user -> new Object[]{user})
                .collect(Collectors.toList());
    }

    /**
     * Проверяем возможные комбинации заполнения анкеты.
     * Данные берутся из файла survey_test_data.json.
     */
    @Test
    public void checkSurveyPage() {
        // Результатом выполнения теста может быть ошибка или успешно добавленная строка в таблицу результатов
        String testResult;

        // Заполняем текстовые поля
        surveyPage.enterEmail(surveyData.getEmail());
        surveyPage.enterName(surveyData.getName());
        surveyPage.selectGender(surveyData.getGender());

        // Проставляем чекбоксы
        surveyPage.checkOption1_1(surveyData.isCheckOption11());
        surveyPage.checkOption1_2(surveyData.isCheckOption12());

        // Выбираем нужный вариант radioButton
        surveyPage.selectRadioOption(surveyData.getRadioOption());
        // Тыкаем кнопку "Добавить"
        surveyPage.clickSubmitButton();

        // Дальше вилка: или запись успешно добавлена, или получена ошибка
        boolean isDialogDisplayed = surveyPage.isOkDialogDisplayed();

        if (isDialogDisplayed) {
            // проверяем, что в диалоге ожидаемое сообщение и закрываем его
            assertEquals(SurveyPage.OK_DIALOG_MESSAGE, surveyPage.getOkDialogMessage());
            surveyPage.okButtonClick();

            // считаем, что строка добавлена - увеличим счетчик
            resultTableRowsCount++;

            assertTrue("При успешном добавлении данных в анкету - ошибки быть не должно",
                    surveyPage.getErrorMessage().isEmpty());

            // за результат будем считать строку, только что добавленную в таблицу;
            // метод getDataTableRow ждёт от нас номер строки, а не их количество.
            testResult = surveyPage.getDataTableRow(resultTableRowsCount - 1);
        } else {
            // за результат будем считать сообщение об ошибке
            testResult = surveyPage.getErrorMessage();
        }

        // сверяем результат теста с ожидаемым результатом
        assertEquals(surveyData.getExpectedOutput(), testResult);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
