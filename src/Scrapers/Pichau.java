package Scrapers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import controller.CriarJson;
import model.PlacasDeVideo;

public class Pichau {
    public static void scrapingPichau(WebDriver driver) {
        ArrayList<PlacasDeVideo> Placas = new ArrayList<>();
        
        driver.get("https://www.google.com/?authuser=0");
        Wait();
        
        driver.get("https://www.pichau.com.br/hardware/placa-de-video");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        while (true) {
            try {
                Wait();
                List<WebElement> nome = driver.findElements(By.xpath("//h2[@class='MuiTypography-root MuiTypography-h6 mui-1jecgbd-product_info_title-noMarginBottom']"));        
                List<WebElement> preco = driver.findElements(By.xpath("//div[@class='mui-1q2ojdg-price_vista']"));        
                List<WebElement> links = driver.findElements(By.xpath("//div[@class='MuiGrid2-root MuiGrid2-direction-xs-row MuiGrid2-grid-xs-6 MuiGrid2-grid-sm-6 MuiGrid2-grid-md-4 MuiGrid2-grid-lg-3 MuiGrid2-grid-xl-2 mui-p3mq1s']//a"));        

                List<String> urlProdutos = new ArrayList<>();
                for (WebElement link : links) {
                    urlProdutos.add(link.getAttribute("href"));
                }

                int tamanhoMinimo = Math.min(Math.min(nome.size(), preco.size()),urlProdutos.size());

                if(tamanhoMinimo > 0){
                    for (int i = 0; i < tamanhoMinimo; i++) {
                        Placas.add(new PlacasDeVideo(nome.get(i).getText(), preco.get(i).getText(), urlProdutos.get(i)));
                    }
                }else{
                    break;
                }

                List<WebElement> proximos = wait.until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//button[@aria-label='Go to next page']")));
                WebElement proximo = proximos.get(0);
                new Actions(driver).scrollToElement(proximo).perform();
                Wait();
                proximo.click();
            } catch (Exception e) {
                break;
            }
        }
        CriarJson.criarArquivoJSON(Placas, "Planilhas/Pichau.json");
    }

    private static void Wait() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
