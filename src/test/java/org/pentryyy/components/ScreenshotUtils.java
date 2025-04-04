package org.pentryyy.components;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "target/screenshots/";
    
    private ScreenshotUtils() {}

    public static void takeScreenshot(WebDriver driver,TestInfo testInfo) {
        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            
        String filename = String
            .format("%s_%s.png", testInfo.getTestMethod().get().getName(), timestamp);
        
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        try {
            FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
