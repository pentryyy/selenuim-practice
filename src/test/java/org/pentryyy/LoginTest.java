package org.pentryyy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentryyy.components.custom.driver.CustomChromeWebDriverManager;
import org.pentryyy.components.ScreenshotUtils;
import org.pentryyy.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    private  AtomicBoolean shouldTakeScreenshot = new AtomicBoolean();

    @BeforeEach
    void setUp() {
        driver = CustomChromeWebDriverManager.getDriver("http://193.233.193.42:9091");
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (shouldTakeScreenshot.get() == true) {
            ScreenshotUtils.takeScreenshot(driver, testInfo);
        }
        CustomChromeWebDriverManager.quitDriver();
    }

    @ParameterizedTest(name = "[{index}] {0}, {1}")
    @CsvFileSource(
        resources      = "/LoginTestData.csv",
        numLinesToSkip = 1,
        delimiter      = ';',
        nullValues     = {"NULL", "N/A"}
    )
    void testLoginWithDataFromCSV(
        String username,
        String password,
        String expectedResult
    ) {
        
        String safeUsername = Optional.ofNullable(username).orElse("");
        String safePassword = Optional.ofNullable(password).orElse("");
        
        loginPage.login(safeUsername, safePassword);
        
        if (expectedResult.equalsIgnoreCase("success")) {            
            shouldTakeScreenshot.set(false);

            CustomChromeWebDriverManager
                .waitFor(10)    
                .until(ExpectedConditions.urlContains("/dashboard"));
            
            Assertions.assertTrue(CustomChromeWebDriverManager.getCurrentUrl()
                                                              .contains("dashboard"),
                          "Пользователь должен перейти в /dashboard");
        } else {
            if (safePassword.isEmpty()) {
                shouldTakeScreenshot.set(true);

                Assertions.assertTrue(loginPage.isPasswordFieldHighlightedRed(), 
                              "Поле пароля должно быть подсвечено красным");
            } else if (safeUsername.isEmpty()) {
                shouldTakeScreenshot.set(true);

                Assertions.assertTrue(loginPage.isUsernameFieldHighlightedRed(),
                              "Поле логина должно быть подсвечено красным");
            } else {
                shouldTakeScreenshot.set(true);
                
                assertTrue(loginPage.isLoginErrorMessageDisplayed(), 
                   "Сообщение об ошибке не отображается");
            }
        }
    }
}
