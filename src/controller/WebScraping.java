package controller;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import Scrapers.Kabum;
import Scrapers.Pichau;
import Scrapers.Terabyte;

public class WebScraping {

    public static void webScraping() {
        
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();

        // Previnir possiveis erros na hora da execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Evitar detecção dos sites
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        try {
            Pichau.scrapingPichau(driver);
            Kabum.scrapingKabum(driver);
            Terabyte.scrapingTerabyte(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            driver.quit();
        }
    }

    
}
