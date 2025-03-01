package controller;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.PlacasDeVideo;

public class CriarPlanilhas {

    public static void criarPlanilhas(ArrayList<PlacasDeVideo> produtos, String nomeArquivo) {
        Workbook pasta = new XSSFWorkbook();

        Sheet planilha = pasta.createSheet("Produtos");

        Font negrito = pasta.createFont();
        negrito.setBold(true);

        CellStyle cellNegrito = pasta.createCellStyle();
        cellNegrito.setFont(negrito);

        CellStyle cellCenter = pasta.createCellStyle();
        cellCenter.setAlignment(HorizontalAlignment.CENTER);

        Row linha = planilha.createRow(0);
        Cell celula1 = linha.createCell(0);
        celula1.setCellValue("Nome");
        celula1.setCellStyle(cellNegrito);
        celula1.setCellStyle(cellCenter);

        Cell celula2 = linha.createCell(1);
        celula2.setCellValue("Preço");
        celula2.setCellStyle(cellNegrito);
        celula2.setCellStyle(cellCenter);

        Cell celula5 = linha.createCell(2);
        celula5.setCellValue("Link");
        celula5.setCellStyle(cellNegrito);
        celula5.setCellStyle(cellCenter);

        planilha.autoSizeColumn(0);
        planilha.autoSizeColumn(1);
        planilha.autoSizeColumn(2);

        if (produtos.size() > 0) {
            int i = 1;
            for (PlacasDeVideo produto : produtos) {
                Row linhaProduto = planilha.createRow(i);
                Cell cellNome = linhaProduto.createCell(0);
                cellNome.setCellValue(produto.getNome());

                Cell cellPreco = linhaProduto.createCell(1);
                cellPreco.setCellValue(produto.getPreco());
                cellPreco.setCellStyle(cellCenter);

                Cell cellLink = linhaProduto.createCell(2);
                cellLink.setCellValue(produto.getLink());
                cellLink.setCellStyle(cellCenter);

                i++;
            }

            try (FileOutputStream arquivo = new FileOutputStream(nomeArquivo)) {
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
}
