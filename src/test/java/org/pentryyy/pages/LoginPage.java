package org.pentryyy.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriverWait wait;

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "[data-test='login-button']")
    private WebElement loginButton;

    @FindBy(css = ".header__text__error.ng-scope")
    private WebElement loginErrorMessage;

    public LoginPage(WebDriver driver) {
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isLoginErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));
            return loginErrorMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordFieldHighlightedRed() {
        try {
            wait.until(ExpectedConditions.attributeContains(passwordField, "class", "ring-input_error"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isUsernameFieldHighlightedRed() {
        try {
            wait.until(ExpectedConditions.attributeContains(usernameField, "class", "ring-input_error"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
