package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import model.Produtos;

public class WebScraping {
    public static void main(String[] args) throws IOException {
        CriarPlanilha(raparDados());
    }

    private static ArrayList<Produtos> raparDados() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        // Caminho do driver
        System.setProperty("webDriver.edge.driver", "resources/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();

/*         options.addArguments("--headless"); */
        // Previnir possiveis erros na hora da execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Evitar detecção dos sites
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);

        WebDriver driver = new EdgeDriver(options);

        driver.get("https://www.amazon.com.br/-/pt");
        for (String iten : Listaitens()) {
            WebElement input = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));
            input.clear();

            input.sendKeys(iten);
            input.submit();

            waitForIt(5000);

            List<WebElement> descricoes = driver.findElements(
                    By.xpath("//h2[@class=\"a-size-base-plus a-spacing-none a-color-base a-text-normal\"]"));
            List<WebElement> preco = driver.findElements(By.xpath("//div[@class=\"a-row a-size-base a-color-base\"]"));

            for (int i = 0; i < descricoes.size(); i++) {
                produtos.add(new Produtos(descricoes.get(i).getText(), preco.get(i).getText()));
            }
        }
        waitForIt(5000);
        driver.close();

        return produtos;
    }

    private static void waitForIt(long i) {
        try {
            new Thread().sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

        private static void CriarPlanilha(ArrayList<Produtos> produtos) {
            Workbook pasta = new XSSFWorkbook();

            Sheet planilha = pasta.createSheet("Produtos");

            Font negrito = pasta.createFont();
            negrito.setBold(true);

            CellStyle cellNegrito = pasta.createCellStyle();
            cellNegrito.setFont(negrito);

            Row linha = planilha.createRow(0);
            Cell celula1 = linha.createCell(0);
            celula1.setCellValue("Nome");
            celula1.setCellStyle(cellNegrito);

            Cell celula2 = linha.createCell(1);
            celula2.setCellValue("A vista");
            celula2.setCellStyle(cellNegrito);

            Cell celula3 = linha.createCell(2);
            celula3.setCellValue("Parcelas");
            celula3.setCellStyle(cellNegrito);

            Cell celula4 = linha.createCell(3);
            celula4.setCellValue("Parcelado");
            celula4.setCellStyle(cellNegrito);

            planilha.autoSizeColumn(0);
            planilha.autoSizeColumn(1);
            planilha.autoSizeColumn(2);
            planilha.autoSizeColumn(3);

            if (produtos.size() > 0) {
                int i = 1;
                for (Produtos produto : produtos) {
                    Row linhaProduto = planilha.createRow(i);
                    Cell cellNome = linhaProduto.createCell(0);
                    cellNome.setCellValue(produto.getDescricao());

                    Cell cellAVista = linhaProduto.createCell(1);
                    cellAVista.setCellValue("R$ " + produto.getaVista().toString());

                    Cell cellParcelas = linhaProduto.createCell(2);
                    cellParcelas.setCellValue(produto.getParcelas() + "X");

                    Cell cellParcelados = linhaProduto.createCell(3);
                    cellParcelados.setCellValue("R$ " + produto.getParcelado().toString());
                    i++;
                }

                try (FileOutputStream arquivo = new FileOutputStream("Produtos.xlsx")) {
                    pasta.write(arquivo);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pasta.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    private static ArrayList<String> Listaitens() {
        ArrayList<String> itens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("itens.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                itens.add(linha);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itens;

    }

}
