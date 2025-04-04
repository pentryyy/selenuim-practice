package org.pentryyy.components.custom.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CustomChromeWebDriverManager extends CustomWebDriverManager {
    
    public static WebDriver getDriver(String url) {

        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();

            WebDriver instance = new ChromeDriver();
            instance.manage().window();
            instance.get(url);
            
            driver.set(instance);
        }
        
        return driver.get();
    }
}