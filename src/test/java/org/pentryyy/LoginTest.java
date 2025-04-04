package org.pentryyy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Optional;

import org.pentryyy.components.ScreenshotUtils;
import org.pentryyy.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private boolean   shouldTakeScreenshot; // Флаг для тех тестов где нужен скриншот

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        driver    = new ChromeDriver();
        loginPage = new LoginPage(driver);

        driver.manage().window();
        driver.get("http://193.233.193.42:9091");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver != null) {
            if (shouldTakeScreenshot) {
                ScreenshotUtils.takeScreenshot(driver, testInfo);
            }
            driver.quit();
        }
    }

    @Test
    void testSuccessfulLogin() {
        shouldTakeScreenshot = false;
        loginPage.login("volikov_mikhail", "2104youtrack");
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("dashboard"));
        assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test
    void testFailureLogin() {
        shouldTakeScreenshot = true;
        loginPage.login("username", "password");
        Assertions.assertTrue(loginPage.isLoginErrorMessageDisplayed(), 
                      "Сообщение об ошибке не отображается");
    }

    @Test
    public void testEmptyPasswordValidation() {
        shouldTakeScreenshot = true;
        loginPage.login("username", "");
        Assertions.assertTrue(loginPage.isPasswordFieldHighlightedRed(), 
                      "Поле пароля должно быть подсвечено красным");
    }

    @Test
    public void testEmptyUsernameValidation() {
        shouldTakeScreenshot = true;
        loginPage.login("", "valid_password");
        Assertions.assertTrue(loginPage.isUsernameFieldHighlightedRed(),
                      "Поле логина должно быть подсвечено красным"
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(
        resources = "/LoginTestData.csv",
        numLinesToSkip = 1,
        delimiter = ';',
        nullValues = {"NULL", "N/A"}
    )
    void testLoginWithDataFromCSV(
        String username,
        String password,
        String expectedResult
    ) {
        
        String safeUsername = Optional.ofNullable(username).orElse("");
        String safePassword = Optional.ofNullable(password).orElse("");
        
        loginPage.login(safeUsername, safePassword);
        
        // Пропуск теста, если логин или пароль пустые (Временно)
        Assumptions.assumeTrue(
            !safeUsername.isEmpty() && !safePassword.isEmpty(),
            "Пропуск: пустой логин или пароль"
        );

        if (expectedResult.equalsIgnoreCase("success")) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("dashboard"));
            assertTrue(driver.getCurrentUrl().contains("dashboard"));
        } else {
            assertTrue(loginPage.isLoginErrorMessageDisplayed(), 
                "Сообщение об ошибке не отображается");
        }
    }
}
