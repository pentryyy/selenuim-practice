package org.pentryyy.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssuesPage {

    private WebDriverWait wait;

    // ----------------------------- Элемент интерфейса Черновики -----------------------------
    @FindBy(xpath = "//*[@id=\"react-root\"]" +
                    "/div/div/div[2]/div/aside" +
                    "/div/div[1]/div/section[1]/div/div[1]")
    private WebElement draft;
    
    @FindBy(css = "section[data-test='drafts-section']")
    private WebElement draftList;

    // ----------------------------- Элемент интерфейса Проекты -------------------------------
    @FindBy(xpath = "//*[@id=\"react-root\"]" +
                    "/div/div/div[2]/div/aside" +
                    "/div/div[1]/div/section[2]/div/div[1]")
    private WebElement project;

    @FindBy(css = "section[data-test='projects-section']")
    private WebElement projectList;

    // ----------------------------- Элемент интерфейса Теги ----------------------------------
    @FindBy(xpath = "//*[@id=\"react-root\"]" +
                    "/div/div/div[2]/div/aside" +
                    "/div/div[1]/div/section[3]/div/div[1]")
    private WebElement tag;

    @FindBy(css = "section[data-test='tags-section']")
    private WebElement tagList;

    // ----------------------------- Элемент интерфейса Сохраненные поиски --------------------
    @FindBy(xpath = "//*[@id=\"react-root\"]" +
                    "/div/div/div[2]/div/aside" +
                    "/div/div[1]/div/section[4]/div/div[1]")
    private WebElement savedSearch;

    @FindBy(css = "section[data-test='saved-searches-section']")
    private WebElement savedSearchList;

    public IssuesPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isDraftListDisplayed() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(draft)).click();
            wait.until(ExpectedConditions.visibilityOf(draftList));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isProjectListDisplayed() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(project)).click();
            wait.until(ExpectedConditions.visibilityOf(projectList));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isTagListDisplayed() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(tag)).click();
            wait.until(ExpectedConditions.visibilityOf(tagList));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSavedSearchListDisplayed() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(savedSearch)).click();
            wait.until(ExpectedConditions.visibilityOf(savedSearchList));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
