package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.example.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        driver    = new ChromeDriver();
        loginPage = new LoginPage(driver);

        driver.manage().window();
        driver.get("http://193.233.193.42:9091");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testSuccessfulLogin() {
        loginPage.login("volikov_mikhail", "2104youtrack");
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("dashboard"));
        assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test
    void testFailureLogin() {
        loginPage.login("username", "password");
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(), 
                      "Сообщение об ошибке не отображается");
    }
}
