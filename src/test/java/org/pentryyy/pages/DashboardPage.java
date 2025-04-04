package org.pentryyy.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private WebDriverWait wait;

    @FindBy(xpath = "//*[@id=\"react-root\"]" +
                    "/div/div/div[2]/main" +
                    "/div/div/div[2]/div[1]/dialog" +
                    "/div/div[2]/div/div/div[1]")
    private WebElement quickNotes;

   
    public DashboardPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isQuickNotesDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(quickNotes));   
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
