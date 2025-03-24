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
            if (nomeFormatado == null || nomeFormatado.isEmpty()) {
                continue;
            }
            produto.setNome(nomeFormatado);
        }

        for (int i = 0; i < produtos.size(); i++) {
            // Seta o produto com menor valor o produto que está no index i
            PlacasDeVideo produtoMenorPreco = produtos.get(i);
            for (int j = 0; j < produtos.size(); j++) {
                // verifica se o produto de index j tem o nome igual ao do produto de index i
                if (produtos.get(i).getNome().equals(produtos.get(j).getNome())) {
                    // verifica se o produto de index j tem o preço menor que o produto de index i
                    if (formatarPreco(produtos.get(j).getPreco()) < formatarPreco(produtos.get(i).getPreco())) {
                        produtoMenorPreco = produtos.get(j);
                    }
                }
            }
            if (!menoresPrecos.contains(produtoMenorPreco)) {
                menoresPrecos.add(produtoMenorPreco);
            }
        }
        CriarJson.criarArquivoJSON(menoresPrecos, "MenorPreco.json");

    }

    private static String formatarNome(String nome) {
        // Padrão para capturar a marca (AMD, NVIDIA, Intel)
        Pattern marcaPattern = Pattern.compile("(AMD|NVIDIA|Intel)", Pattern.CASE_INSENSITIVE);
        Matcher marcaMatcher = marcaPattern.matcher(nome);
        String marca = marcaMatcher.find() ? marcaMatcher.group(1) : "";

        // Padrão para capturar o tipo da placa (Radeon, GeForce, ARC)
        Pattern tipoPattern = Pattern.compile("(Radeon|GeForce|ARC)", Pattern.CASE_INSENSITIVE);
        Matcher tipoMatcher = tipoPattern.matcher(nome);
        String tipo = tipoMatcher.find() ? tipoMatcher.group(1) : "";

        // Padrão para capturar a nomenclatura (RX 6600, RTX 3060, etc.)
        Pattern nomenclaturaPattern = Pattern.compile("(RX|RTX|GTX|R9|Arc)\\s?[A-Za-z0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher nomenclaturaMatcher = nomenclaturaPattern.matcher(nome);
        String nomenclatura = nomenclaturaMatcher.find() ? nomenclaturaMatcher.group(0) : "";

        // Padrão para capturar a fabricante
        Pattern marcaPlacaPattern = Pattern.compile("(ASUS|Gigabyte|MSI|Zotac|EVGA|PNY|Inno3D|Colorful|Palit|Leadtek|Gainward|ASRock|Sapphire|XFX|PowerColor|VTX3D|Diamond|Club 3D|TUL|Manli|Maxsun)", Pattern.CASE_INSENSITIVE);
        Matcher marcaPlacaMatcher = marcaPlacaPattern.matcher(nome);
        String marcaPlaca = marcaPlacaMatcher.find() ? marcaPlacaMatcher.group(1) : "";

        // Construir o nome normalizado
        return String.format("Placa de Vídeo %s %s %s %s", marca, tipo, nomenclatura, marcaPlaca).trim();
    }

    private static Double formatarPreco(String preco) {
        if (preco.contains("R$ ----")) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(preco.replaceAll("[R$.]", "").replace(",", "."));

    }
}
