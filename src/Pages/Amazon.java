package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import model.PlacasDeVideo;

public class Amazon {
    public static void scrapingAmazon(WebDriver driver) {
        ArrayList<PlacasDeVideo> placas = new ArrayList<>();
        String parteUrl = "s?i=computers&bbn=17351089011&rh=n%3A16339926011%2Cn%3A17351089011%2Cn%3A16364750011%2Cn%3A16364811011&dc&page=&xpid=kAUOPjyxvwari";

        String url = "https://www.amazon.com.br/" + parteUrl;

        driver.get(url);

        List<WebElement> nome = driver.findElements(
                By.xpath("//h2[@class='a-size-base-plus a-spacing-none a-color-base a-text-normal']//span"));
        
        for (int j = 0; j < nome.size(); j++) {
            System.out.println(nome.get(j).getText());
        }
        System.out.println("=---------------------------------------=");
    }
}
