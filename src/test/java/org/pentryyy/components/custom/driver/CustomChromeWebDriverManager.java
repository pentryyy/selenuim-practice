package org.pentryyy.components.custom.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class CustomChromeWebDriverManager extends CustomWebDriverManager {
    
    public static WebDriver getDriver(String url) {

        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();

            WebDriver instance = new ChromeDriver();
            instance.manage().window();
            instance.manage().timeouts()
                    .pageLoadTimeout(Duration.ofSeconds(20))
                    .implicitlyWait(Duration.ofSeconds(30))
                    .scriptTimeout(Duration.ofSeconds(10));
            instance.get(url);
            
            driver.set(instance);
        }
        
        return driver.get();
    }
}
