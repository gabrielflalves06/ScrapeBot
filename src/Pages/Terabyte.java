package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import controller.CriarPlanilhas;
import controller.WebScraping;
import model.PlacasDeVideo;

public class Terabyte extends WebScraping {

    public static void scrapigTerabyte(WebDriver driver) throws InterruptedException {

        ArrayList<PlacasDeVideo> Placas = new ArrayList<>();

        driver.get("https://www.terabyteshop.com.br/hardware/placas-de-video");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement botaoPopUp = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class=\"tsG0HQh7bcmTha7pyanx-box-btn tsG0HQh7bcmTha7pyanx-btn-close\"]")));
        botaoPopUp.click();

        WebElement botaoPropagando = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"fa fa-times\"]")));
        botaoPropagando.click();

        WebElement botaoContinuar = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btfchar']")));
        botaoContinuar.click();

        Thread.sleep(10000);
        while (true) {
            try {
                WebElement button = driver.findElement(By.xpath("//a[@class='arrow-down btn btn-pdmore']"));
                new Actions(driver).scrollToElement(button).perform();
                Thread.sleep(1000);
                button.click();
                Thread.sleep(5000);
                
            } catch (Exception e) {
                System.out.println("acabou as paginas");
                break;
            }
        }

        List<WebElement> aVista = driver.findElements(By.xpath("//div[@class=\"product-item__new-price\"]"));
        List<WebElement> links = driver.findElements(By.cssSelector(".product-item__name"));

        List<String> UrlProduto = new ArrayList<>();
        List<String> nome = new ArrayList<>();
        for (WebElement link : links) {
            UrlProduto.add(link.getAttribute("href"));
            nome.add(link.getAttribute("title"));
        }

        for (int j = 0; j < nome.size(); j++) {
            if (j < aVista.size()) {
                Placas.add(new PlacasDeVideo(nome.get(j),
                        aVista.get(j).getText(),
                        UrlProduto.get(j)));
            }
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        System.out.println("criando o arquivo");
        CriarPlanilhas.criarPlanilhas(Placas, "Terabyte.xlsx");
    }

}
