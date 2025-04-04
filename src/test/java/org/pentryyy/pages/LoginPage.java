package org.pentryyy.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver     driver;
    private WebDriverWait wait;

    private static final By USERNAME_FIELD      = By.id("username");
    private static final By PASSWORD_FIELD      = By.id("password");
    private static final By LOGIN_BUTTON        = By.cssSelector("[data-test='login-button']");
    private static final By LOGIN_ERROR_MESSAGE = By.cssSelector(".header__text__error.ng-scope");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void login(String username, String password) {
        waitForElement(USERNAME_FIELD);
        driver.findElement(USERNAME_FIELD).sendKeys(username);
        
        waitForElement(PASSWORD_FIELD);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        
        waitForElement(LOGIN_BUTTON);
        driver.findElement(LOGIN_BUTTON).click();
    }

    public boolean isLoginErrorMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_ERROR_MESSAGE))
                   .isDisplayed();
    }

    public boolean isPasswordFieldHighlightedRed() {
        try {
            wait.until(ExpectedConditions.attributeContains(PASSWORD_FIELD, "class", "ring-input_error"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isUsernameFieldHighlightedRed() {
        try {
            wait.until(ExpectedConditions.attributeContains(USERNAME_FIELD, "class", "ring-input_error"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
