package org.pentryyy.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgilesPage {

    private WebDriverWait wait;

    // ------------------------ Данные для теста по добавлению карточки -----------------------
    @FindBy(xpath = "//button[@class='yt-agile-board__create-button_new button_f3d2 button_f3d2 heightS_c114 primary_e13e buttonWithoutIcon_b3e8']")
    WebElement dropdown;

    @FindBy(xpath = "//span[@title='Свимлэйн...']")
    WebElement swimlane;

    @FindBy(xpath = "//form[@class='ticketForm__fa46 ticketEditForm__f5b8']")
    WebElement formOfCard;

    @FindBy(xpath = "//button[@data-test='Новая карточка']")
    private WebElement addNewCardButton;

    @FindBy(xpath = "//textarea[@data-test='summary']")
    private WebElement textarea;

    @FindBy(xpath = "//div[@data-test='wysiwyg-editor']")
    private WebElement editor;
   
    public AgilesPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);    
    }

    public boolean isNewCardAdded() {
            String timestamp = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String heading     = "Тестовый заголовок " + timestamp; 
            String description = "Тестовое описание "  + timestamp;

            wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
            wait.until(ExpectedConditions.elementToBeClickable(swimlane)).click();

            wait.until(ExpectedConditions.elementToBeClickable(textarea)).sendKeys(heading);
            wait.until(ExpectedConditions.elementToBeClickable(editor)).sendKeys(description);

            return true;
    }
}
