package org.pentryyy;

import java.util.concurrent.atomic.AtomicBoolean;

import org.pentryyy.components.custom.driver.CustomChromeWebDriverManager;
import org.pentryyy.components.ScreenshotUtils;
import org.pentryyy.pages.IssuesPage;
import org.pentryyy.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class IssuesTest {

    private WebDriver  driver;
    private LoginPage  loginPage;
    private IssuesPage issuesPage;

    private AtomicBoolean shouldTakeScreenshot = new AtomicBoolean();

    @BeforeEach
    void setUp() {
        driver = CustomChromeWebDriverManager.getDriver("http://193.233.193.42:9091/issues");
        
        issuesPage = new IssuesPage(driver);
        loginPage  = new LoginPage(driver);

        loginPage.login("volikov_mikhail", "2104youtrack");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (shouldTakeScreenshot.get() == true) {
            ScreenshotUtils.takeScreenshot(driver, testInfo);
        }
        CustomChromeWebDriverManager.quitDriver();
    }

    @Test
    void testUserInterfaceLoaded() {
        try {

            Assertions.assertTrue(issuesPage.isDraftListDisplayed(),
                          "Список черновиков не отображается");

            Assertions.assertTrue(issuesPage.isProjectListDisplayed(),
                          "Список проектов не отображается");

            Assertions.assertTrue(issuesPage.isTagListDisplayed(),
                          "Список тегов не отображается");

            Assertions.assertTrue(issuesPage.isSavedSearchListDisplayed(),
                          "Список сохраненных поисков не отображается");
                
            shouldTakeScreenshot.set(false);
        } catch (Throwable e) {
            shouldTakeScreenshot.set(true);
            throw e;
        }
    }
    
    @Test
    void testAddNewTask() {
        try {

            Assertions.assertTrue(issuesPage.isNewTaskAdded(null),
                          "Новая задача не добавлена");
            
            shouldTakeScreenshot.set(false);
        } catch (Throwable e) {
            shouldTakeScreenshot.set(true);
            throw e;
        }
    }

    /* 
    Предполагается, что на странице уже содержится какие либо записи.
    Этот тест ищет состояние - "Завершенная (Готово)".
    Проверяется соответствие фильра текущему резульатту.
    */
    @Test
    void testConditionTaskFilter() {
        try {

            Assertions.assertTrue(issuesPage.isConditionTaskFiltered(),
                          "Фильтр задач не корректно работает для состояния");
            
            shouldTakeScreenshot.set(false);
        } catch (Throwable e) {
            shouldTakeScreenshot.set(true);
            throw e;
        }
    }

    /* 
    Предполагается, что на странице уже содержится какие либо записи.
    Этот тест ищет проект - "K1dDoymwre (id K1D-*)".
    Проверяется соответствие фильра текущему резульатту.
    */
    @Test
    void testProjectTaskFilter() {
        try {

            Assertions.assertTrue(issuesPage.isProjectTaskFiltered(),
                          "Фильтр задач не корректно работает для ролекта");
            
            shouldTakeScreenshot.set(false);
        } catch (Throwable e) {
            shouldTakeScreenshot.set(true);
            throw e;
        }
    }
}
