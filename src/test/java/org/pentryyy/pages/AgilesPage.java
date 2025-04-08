package org.pentryyy.pages;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgilesPage {

    private WebDriver     driver;
    private WebDriverWait wait;

    // ------------------------ Данные для теста по добавлению карточки -----------------------

    public AgilesPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isNewCardAdded() {
        try {

            // String timestamp = LocalDateTime
            //     .now()
            //     .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
            // String heading     = "Тестовый заголовок " + timestamp; 
            // String description = "Тестовое описание "  + timestamp;

            Thread.sleep(10000);

            WebElement dropdown = driver.findElement(By.xpath(
                "//button[@class='yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8']"
            ));
            dropdown.click();

            Thread.sleep(10000);
            WebElement swimlane = driver.findElement(By.xpath(
                "//button[@id=\"list-item-0-jcn4\"]"
            ));      
            
            // swimlane.click();      

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", swimlane
            );

            // Select select = new Select(dropdown);
            // select.selectByVisibleText("Свимлэйн...");


            // yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8
            // yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8
            // yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8
            // yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8

            // wait.until(ExpectedConditions.elementToBeClickable(swimlaneButton)).click();

            // By buttonLocator = By.xpath("//button[contains(@class, 'action_f9dd') and @data-test='ring-list-item-label']");
            // wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

            // WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator));
            // button = wait.until(d -> {
            //     driver.navigate().refresh();  // если требуется обновление
            //     return driver.findElement(buttonLocator);
            // });

            // WebElement swimlane = wait.until(ExpectedConditions.presenceOfElementLocated(
            //     By.xpath("//span[@class='label_f8a6' and contains(text(),'Свимлэйн')]")
            // ));


            // By buttonSelector = By.cssSelector("button[data-test='ring-list-item-label']");
            // WebElement button = driver.findElement(buttonSelector);
            // button.click();

            // System.out.println(swimlaneButton.getText()+"\n");

            // swimlaneButton.click();

            // wait.until(ExpectedConditions.elementToBeClickable(textarea)).sendKeys(heading);
            // wait.until(ExpectedConditions.elementToBeClickable(editor)).sendKeys(description);
            
            return true;
        } catch (TimeoutException | InterruptedException e) {
            return false;
        }
    }
}
