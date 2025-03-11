package controller;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.PlacasDeVideo;

public class CriarJson {
    public static void criarArquivoJSON(ArrayList<PlacasDeVideo> produtos, String nomeDoArquivo) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Planilhas/" + nomeDoArquivo), produtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
