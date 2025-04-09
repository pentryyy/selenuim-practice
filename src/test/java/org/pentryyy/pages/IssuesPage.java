package org.pentryyy.pages;

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
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssuesPage {

    private WebDriver     driver;
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

    // ----------------------- Данные для теста по добавлению новой задачи --------------------
    @FindBy(xpath = "//*[@id=\"top-bar-right-section-primary-portal\"]/div/a")
    private WebElement newTaskButton;

    @FindBy(css = "textarea[data-test='summary']")
    private WebElement textarea;

    @FindBy(css = "div[data-test='wysiwyg-editor-content']")
    private WebElement editor;

    @FindBy(css = "button[data-test='submit-button']")
    private WebElement submitButton;

    @FindBy(css = "div.firstColumn__d993")
    private WebElement mainInfoAboutTask;

    // -------------------- Данные для теста по поиску существующей задачи --------------------
    @FindBy(css = "input[data-test='ring-select__focus']")
    private WebElement searchInput;

    @FindBy(css = "table[data-test='ring-table']")
    private WebElement resultsTableAfterSearch;
    
    @FindBy(css = "td[data-test='ring-table-cell summary']")
    private List<WebElement> summaryCells;

    // ------------------- Данные для теста по удалению существующей задачи -------------------
    @FindBy(css = "tr[data-test^='ring-table-row']")
    private List<WebElement> tableRows;

    @FindBy(className = "ring-ui-input_c2c4")
    WebElement checkbox;

    @FindBy(css = "button[data-test='delete-item-button']")
    WebElement deleteButton;

    @FindBy(css = "button[data-test='confirm-ok-button']")
    WebElement okButton;

    @FindBy(xpath = "//h1[contains(text(),'По вашему запросу не найдено ни одной задачи')]")
    WebElement notFoundTasksMessage;

    // -------------------- Данные для теста фильра существующх задач -------------------------
    @FindBy(xpath = "//button[@class = '" +
                    "ring-ui-button_bc66 addButton__c4f8 " + 
                    "ring-ui-heightS_de02 " +
                    "ring-ui-withIcon_b32b " +
                    "ring-ui-withNormalIcon_cefa']")
    private WebElement filterButton;

    @FindBy(xpath = "//span[@data-test='ring-list-item-label']")
    private WebElement filterStatus;

    @FindBy(xpath = "//span[@title='Состояние']")
    private WebElement conditionFilter;

    @FindBy(xpath = "//span[@title='Проект']")
    private WebElement projectFilter;

    public IssuesPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public boolean isNewTaskAdded(String heading) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(newTaskButton)).click();
            
            String timestamp = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            
            if (heading == null)
                heading = "Тестовый заголовок " + timestamp; 

            String description = "Тестовое описание "  + timestamp;     

            wait.until(ExpectedConditions.elementToBeClickable(textarea)).sendKeys(heading);
            wait.until(ExpectedConditions.elementToBeClickable(editor)).sendKeys(description);
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
            
            wait.until(ExpectedConditions.visibilityOf(mainInfoAboutTask));

            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isTaskFinded(String searchQuery) {
        try {
            if (searchQuery == null) {
                return false;
            }

            wait.until(ExpectedConditions.elementToBeClickable(searchInput))
                .sendKeys(searchQuery + Keys.ENTER);
            
            wait.until(ExpectedConditions.visibilityOf(resultsTableAfterSearch));
            
            return summaryCells.stream()
                               .anyMatch(cell -> cell.getText().contains(searchQuery));
           
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isTaskDeleted(String searchQuery) {
        try {
            if (searchQuery == null) {
                return false;
            }
            
            wait.until(ExpectedConditions.elementToBeClickable(searchInput))
                .sendKeys(searchQuery + Keys.ENTER);
            
            wait.until(ExpectedConditions.visibilityOf(resultsTableAfterSearch));
            
            for (WebElement row : tableRows) {
                if (row.getText().contains(searchQuery)) {

                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", checkbox
                    );

                    wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
                    wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
                    wait.until(ExpectedConditions.visibilityOf(notFoundTasksMessage));

                    return true;
                }
            }

            return false;

        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isConditionTaskFiltered() {

        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(conditionFilter)).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterStatus)).click();

        List<WebElement> rowsOfFilter = driver.findElements(By.xpath(
            "//tbody[@data-test='ring-table-body']" +
            "//span[contains(@class, 'resolved__c7d5')]"
        ));

        if (rowsOfFilter.size() == 0)
            return false;
        
        return rowsOfFilter.get(0)
                           .getText()
                           .contains("Готово");
    }

    public boolean isProjectTaskFiltered() {

        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectFilter)).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterStatus)).click();

        List<WebElement> rowsOfFilter = driver.findElements(By.xpath(
            "//tbody[@data-test='ring-table-body']" +
            "//a[contains(@class, 'itemId__a877')]"
        ));

        if (rowsOfFilter.size() == 0)
            return false;

        return rowsOfFilter.get(0)
                           .getText()
                           .contains("K1D");
    }
}
