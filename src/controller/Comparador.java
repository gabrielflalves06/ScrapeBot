package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.PlacasDeVideo;

public class Comparador {
    public static void LerArquivo() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File pasta = new File("Planilhas/");
            File[] arquivos = pasta.listFiles();
            List<PlacasDeVideo> produtos = new ArrayList<>();
            for (File arquivo : arquivos) {
                System.out.println(arquivo.getName());
                List<PlacasDeVideo> produtosArquivo = objectMapper.readValue(arquivo,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PlacasDeVideo.class));
                produtos.addAll(produtosArquivo);
            }
            Comparar(produtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Comparar(List<PlacasDeVideo> produtos) {
        ArrayList<PlacasDeVideo> menoresPrecos = new ArrayList<>();

        for (PlacasDeVideo produto : produtos) {
            String nomeFormatado = formatarNome(produto.getNome());
            if(nomeFormatado == null || nomeFormatado.isEmpty()){
               continue;
            }
            produto.setNome(nomeFormatado);
        }

        for (int i = 0; i < produtos.size(); i++) {
            //Seta o produto com menor valor o produto que está no index i
            PlacasDeVideo produtoMenorPreco = produtos.get(i);
            for (int j = 0; j < produtos.size(); j++) {
                //verifica se o produto de index j tem o nome igual ao do produto de index i
                if (produtos.get(i).getNome().equals(produtos.get(j).getNome())) {
                    //verifica se o produto de index j tem o preço menor que o produto de index i
                    if (formatarPreco(produtos.get(j).getPreco()) < formatarPreco(produtos.get(i).getPreco())) {
                        produtoMenorPreco = produtos.get(j);
                    }
                }
            }
            if(!menoresPrecos.contains(produtoMenorPreco)){
                menoresPrecos.add(produtoMenorPreco);
            }
        }
        CriarJson.criarArquivoJSON(menoresPrecos, "MenorPreco.json");

    }

    private static String formatarNome(String nome) {
        String nomeLimpo = nome.replaceAll(
                "(?i)(Placa de Vídeo|Gaming|OC Edition|Super|Dual|Ventus|Eagle|TUF|Speedster|Challenger|1-Click OC|WINDFORCE|Steel Legend|EX Plus)",
                "");

        // Extrair fabricante (NVIDIA ou AMD)
        String fabricante = nome.contains("NVIDIA") ? "NVIDIA" : (nome.contains("AMD") ? "AMD" : "");

        // Extrair modelo (Exemplo: RTX 3060, RX 6600)
        Pattern pattern = Pattern.compile("((RTX|GTX|Rx|GT|Arc|Gt|R5|Radeon|Quadro|G|RX)\\s?([0-9]+(?:\\s?[A-Z]*)?)|[A-Za-z]\\w*)");
        Matcher matcher = pattern.matcher(nomeLimpo);
        String modelo = matcher.find() ? matcher.group() : "";

        // Retornar o nome padronizado
        return (fabricante + " " + modelo).trim();
    }

    private static Double formatarPreco(String preco) {
        if (preco.contains("R$ ----")) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(preco.replaceAll("[R$.]", "").replace(",", "."));

    }
}
