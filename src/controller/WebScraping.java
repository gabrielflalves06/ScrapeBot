package controller;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import Pages.Amazon;
import Pages.Kabum;
import Pages.Terabyte;

public class WebScraping {

    public static void webScraping() {
        
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();

        // Previnir possiveis erros na hora da execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Evitar detecção dos sites
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

        WebDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        try {
/*             Kabum.scrapingKabum(driver);
            Terabyte.scrapingTerabyte(driver); */
            Amazon.scrapingAmazon(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            driver.quit();
        }
    }

    
}
