package ru.dfgnkkr.forprotei.autotests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Абстрактная страница
 */
public abstract class Page {

    protected WebDriver driver;
    protected final File file = new File("./src/test/resources/qa-test.html");

    /**
     * Конструктор страницы
     */
    public Page(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Отображается ли страница.
     */
    public abstract boolean isPageDisplayed();

    /**
     * Возвращает текст отображаемой ошибки.
     */
    public abstract String getErrorMessage();

    /**
     * Открываем наш файл.
     * Файл/адрес у нас один - значит нет смысла переопределять метод в дочерних классах.
     */
    public void open() {
        // Преобразуем путь к файлу в URI и открываем в браузере
        driver.get(file.toURI().toString());
    }

    /**
     * Выполняет поиск элемента на странице по id.
     *
     * @param id селектор для поиска элемента на странице
     * @return элемент, найденный на странице по переданному id
     * @throws NoSuchElementException если элемент не найден в течении заданного таймаута
     */
    public WebElement findElementById(String id) throws NoSuchElementException {
        return driver.findElement(By.id(id));
    }

    /**
     * Выполняет поиск элемента на странице по xpath.
     *
     * @param xpath селектор для поиска элемента на странице
     * @return элемент, найденный на странице по переданному xpath
     * @throws NoSuchElementException если элемент не найден в течении заданного таймаута
     */
    public WebElement findElementByXPath(String xpath) throws NoSuchElementException {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Ищет по переданному селектору элемент на странице и проверяет его отображение.
     *
     * @param by селектор для поиска элемента на странице.
     * @return true, если элемент найден и отображается на странице
     */
    public boolean isElementDisplayed(By by) {
        WebElement element;
        try {
            element = driver.findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return element.isDisplayed();
    }

    /**
     * Проверяет отображение элемента на странице.
     *
     * @param element элемент, который пытаемся найти на странице
     * @return true, если элемент найден и отображается на странице
     */
    public boolean isElementDisplayed(WebElement element) {
        boolean result;
        try {
            result = element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return result;
    }
}