package org.pentryyy.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AgilesPage {

    private WebDriver     driver;
    private WebDriverWait wait;

    // ------------------------ Данные для теста по добавлению карточки -----------------------
    @FindBy(xpath = "//button[@class = '" +
                    "yt-agile-board__create-button_new " +
                    "button_f3d2 " +
                    "button_f3d2 " +
                    "heightS_c114 " +
                    "primary_e13e " +
                    "buttonWithoutIcon_b3e8']")
    WebElement dropdown;

    @FindBy(xpath = "//div[@class='itemContainer_a66c']")
    List<WebElement> buttons;

    @FindBy(xpath = "//textarea[@data-test='summary']")
    private WebElement swimlaneHeader;

    @FindBy(xpath = "//div[@data-test='wysiwyg-editor-content']")
    private WebElement swimlaneDescription;

    @FindBy(xpath = "//button[@class = '" +
                    "ring-ui-button_bc66 " +
                    "ring-ui-heightS_de02 " +
                    "ring-ui-primary_ea44']")
    private WebElement swimlaneButton;

    @FindBy(xpath = "//input[@id = 'boardName']")
    private WebElement sprintName;

    @FindBy(xpath = "//textarea[@class='" +
                    "ring-input " +
                    "ng-pristine " +
                    "ng-untouched " +
                    "ng-valid ng-empty']")
    private WebElement sprintTarget;

    @FindBy(xpath = "//button[.//ng-transclude[normalize-space()='Создать спринт']]")
    private WebElement sprintButton;

    /*
    Поиск элементов выпадающего списка 
    Свимлейн[0]
    Спринт[1]
    Доски[2]
    */
    private WebElement findElementByDropdownId(int id) {
        List<String> ids = new ArrayList<>();
        
        for (WebElement item : buttons)
            ids.add(
                item.findElement(By.tagName("button")).getAttribute("id")
            );
        
        return driver.findElement(By.id(ids.get(id)));
    }

    public AgilesPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);    
    }

    public boolean isNewSwimlaneCardAdded() {
        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String heading     = "Тестовый заголовок " + timestamp; 
        String description = "Тестовое описание "  + timestamp;

        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        
        WebElement firstButton = findElementByDropdownId(0);
        
        try {
            Thread.sleep(500);
            firstButton.click();

            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(swimlaneHeader)).sendKeys(heading);
            wait.until(ExpectedConditions.elementToBeClickable(swimlaneDescription)).sendKeys(description);
            wait.until(ExpectedConditions.elementToBeClickable(swimlaneButton)).click();

            driver.findElement(By.xpath(
                "//span[@data-test='yt-agile-board-swimlane__summary' " +
                "and text()='" + heading + "']"
            ));

            return true;

        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean isNewSprintCardAdded() {
        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String heading     = "Тестовый заголовок " + timestamp; 
        String description = "Тестовое описание "  + timestamp;

        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        
        WebElement secondButton = findElementByDropdownId(1);
        
        try {
            Thread.sleep(500);
            secondButton.click();

            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(sprintName)).sendKeys(heading);
            wait.until(ExpectedConditions.elementToBeClickable(sprintTarget)).sendKeys(description);
            wait.until(ExpectedConditions.elementToBeClickable(sprintButton)).click();
            
            driver.findElement(By.xpath(
                ".//div[contains(@class, 'yt-agile-board__toolbar__sprint__select__text') " + 
                "and normalize-space()='" + heading + "']"
            ));

            return true;

        } catch (InterruptedException e) {
            return false;
        }
    }
}
