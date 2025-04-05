package org.pentryyy;

import java.util.concurrent.atomic.AtomicBoolean;

import org.pentryyy.components.custom.driver.CustomChromeWebDriverManager;
import org.pentryyy.components.ScreenshotUtils;
import org.pentryyy.pages.DashboardPage;
import org.pentryyy.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class DashboardTest {

    private WebDriver     driver;
    private LoginPage     loginPage;
    private DashboardPage dashboardPage;

    private AtomicBoolean shouldTakeScreenshot = new AtomicBoolean();

    @BeforeEach
    void setUp() {
        driver = CustomChromeWebDriverManager.getDriver("http://193.233.193.42:9091");
        
        dashboardPage = new DashboardPage(driver);
        loginPage     = new LoginPage(driver);

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
            
            Assertions.assertTrue(dashboardPage.isIssueListDisplayed(),
                        "Список ошибок не отображаются");

            Assertions.assertTrue(dashboardPage.isQuickNotesDisplayed(),
                        "Заметки не отображаются");

            shouldTakeScreenshot.set(false);
        } catch (Throwable e) {
            shouldTakeScreenshot.set(true);
            throw e;
        }
    }
}
