package org.pentryyy.components.custom.driver;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class CustomWebDriverManager {
    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    static WebDriver getDriver(String url) {
        throw new UnsupportedOperationException("Must be implemented");
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public static String getCurrentUrl() {
        return driver.get().getCurrentUrl();
    }
    
    public static WebDriverWait waitFor(long seconds) {
        return new WebDriverWait(driver.get(), Duration.ofSeconds(seconds));
    }
}
