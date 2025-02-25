package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/* import model.Placas; */

public class Main {

    public static void main(String[] args) throws IOException {
        TentarEntrarNoProduto();

    }

    private static void TentarEntrarNoProduto() {
        /* ArrayList<Placas> produtos = new ArrayList<>(); */
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();

        // Previnir possiveis erros na hora da execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Evitar detecção dos sites
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);

        WebDriver driver = new EdgeDriver(options);

        for (int i = 1; i <= 6; i++) {
            String url = String.format(
                    "https://www.kabum.com.br/hardware/placa-de-video-vga?page_number=%d&page_size=100&facet_filters=&sort=most_searched",
                    i);
            driver.get(url);

            waitForIt(5000);

            List<WebElement> links = driver.findElements(By.xpath("//a[@Class=\"sc-27518a44-4 kVoakD productLink\"]"));
            List<String> UrlProduto = new ArrayList<>();
            for (WebElement link : links) {
                UrlProduto.add(link.getAttribute("href"));
            }

            for (String urls : UrlProduto) {
                driver.get(urls);

                List<WebElement> nome = driver.findElements(By.xpath("//h1[@Class=\"sc-58b2114e-6 brTtKt\"]"));
                List<WebElement> aVista = driver.findElements(By.xpath("//h4[@Class=\"sc-5492faee-2 ipHrwP finalPrice\"]"));
                String descricao = driver.findElement(By.xpath("//div[@class=\"sc-7e0ca514-1 GiPKU\"]")).getText();
                String marca = descricao.contains("Marca")? descricao.substring(descricao.indexOf("Marca"), descricao.indexOf("\n", descricao.indexOf("Marca"))) : "N/A";
                String modelo = descricao.contains("Modelo")? descricao.substring(descricao.indexOf("Modelo"), descricao.indexOf("\n", descricao.indexOf("Modelo"))) : "N/A";

                for (int j = 0; j < nome.size(); j++) {
                    if (j < aVista.size()) {
                        System.out.printf("%s\n%s\n%s\n%s\n----------------------\n",nome.get(j).getText(), aVista.get(j).getText(),marca,modelo);
                    }
                }
            }
        }
        waitForIt(5000);
        driver.quit();  
        /* CriarPlanilha(produtos); */
    }

    public static void waitForIt(long i) {
        try {
            new Thread();
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * private static void CriarPlanilha(ArrayList<Placas> produtos) {
     * Workbook pasta = new XSSFWorkbook();
     * 
     * Sheet planilha = pasta.createSheet("Produtos");
     * 
     * Font negrito = pasta.createFont();
     * negrito.setBold(true);
     * 
     * CellStyle cellNegrito = pasta.createCellStyle();
     * cellNegrito.setFont(negrito);
     * 
     * CellStyle cellCenter = pasta.createCellStyle();
     * cellCenter.setAlignment(HorizontalAlignment.CENTER);
     * 
     * Row linha = planilha.createRow(0);
     * Cell celula1 = linha.createCell(0);
     * celula1.setCellValue("Nome");
     * celula1.setCellStyle(cellNegrito);
     * celula1.setCellStyle(cellCenter);
     * 
     * Cell celula2 = linha.createCell(1);
     * celula2.setCellValue("Modelo");
     * celula2.setCellStyle(cellNegrito);
     * celula2.setCellStyle(cellCenter);
     * 
     * planilha.autoSizeColumn(0);
     * planilha.autoSizeColumn(1);
     * 
     * if (produtos.size() > 0) {
     * int i = 1;
     * for (Placas produto : produtos) {
     * Row linhaProduto = planilha.createRow(i);
     * Cell cellNome = linhaProduto.createCell(0);
     * cellNome.setCellValue(produto.getNome());
     * 
     * Cell cellAVista = linhaProduto.createCell(1);
     * cellAVista.setCellValue(produto.getModelo());
     * cellAVista.setCellStyle(cellCenter);
     * 
     * i++;
     * }
     * 
     * try (FileOutputStream arquivo = new FileOutputStream("Teste.xlsx")) {
     * pasta.write(arquivo);
     * } catch (Exception e) {
     * e.printStackTrace();
     * } finally {
     * try {
     * pasta.close();
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     * }
     * }
     */
}