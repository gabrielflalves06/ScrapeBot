package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import controller.CriarPlanilhas;
import controller.WebScraping;
import model.PlacasDeVideo;

public class Kabum extends WebScraping {

    public static void scrapigKabum(WebDriver driver) {

        ArrayList<PlacasDeVideo> Placas = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            String url = String.format("https://www.kabum.com.br/hardware/placa-de-video-vga?page_number=%d&page_size=100&facet_filters=&sort=most_searched", i);
            driver.get(url);

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            List<WebElement> nome = driver.findElements(By.cssSelector(".nameCard"));
            List<WebElement> aVista = driver.findElements(By.cssSelector(".priceCard"));
            List<WebElement> links = driver.findElements(By.cssSelector(".productLink"));

            List<String> UrlProduto = new ArrayList<>();
            for (WebElement link : links) {
                UrlProduto.add(link.getAttribute("href"));
            }

            for (int j = 0; j < nome.size(); j++) {
                if (j < aVista.size()) {
                    Placas.add(new PlacasDeVideo(nome.get(j).getText(),
                            aVista.get(j).getText().contains("R$") ? aVista.get(j).getText() : "Preço indisponivel ",
                            UrlProduto.get(j)));
                }
            }
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        CriarPlanilhas.criarPlanilhas(Placas, "Kabum.xlsx");
    }
}
