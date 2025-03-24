package controller;

public class Main {

    public static void main(String[] args){
        long tempoInicial = System.currentTimeMillis();
        WebScraping.webScraping();
        Comparador.LerArquivo();
        long tempoFinal = System.currentTimeMillis();
        System.out.println( tempoFinal - tempoInicial );
    }
}