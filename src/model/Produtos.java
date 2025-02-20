package model;

import java.math.BigDecimal;

public class Produtos {
    private String descricao;
    private String dadosValores;
    private BigDecimal aVista;
    private BigDecimal parcelado;
    private int parcelas;
    

    public Produtos(String descricao, String dadosValores) {
        this.descricao = descricao;
        this.dadosValores = dadosValores;
        this.parcelas = getParcelas(dadosValores);
        this.aVista = getaVista(dadosValores);
        this.parcelado = getParcelado(dadosValores);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDadosValores() {
        return dadosValores;
    }

    public void setDadosValores(String dadosValores) {
        this.dadosValores = dadosValores;
    }

    public BigDecimal getaVista(){
        return aVista;
    }

    public BigDecimal getaVista(String dadosvalores) {
        int indexStart = dadosValores.indexOf('$');
        int indexEnd = dadosValores.indexOf('\n');

        if (indexStart == -1 || indexEnd == -1 || indexStart >= indexEnd) {
            return BigDecimal.ZERO; // Retorna 0 caso os valores estejam ausentes ou mal formatados
        }

        String valor = dadosValores.substring(indexStart + 1, indexEnd)
                .replace("$", "")
                .replaceAll("\\.", "");

        if (indexEnd + 3 > dadosValores.length()) {
            return new BigDecimal(valor);
        }

        valor += "." + dadosValores.substring(indexEnd + 1, indexEnd + 3).trim();

        return new BigDecimal(valor);
    }

    public void setaVista(BigDecimal aVista) {
        this.aVista = aVista;
    }

    public BigDecimal getParcelado(){
        return parcelado;
    }

    public BigDecimal getParcelado(String dadosValores) {
        if (this.parcelas == 0) {
            return BigDecimal.ZERO;
        }

        String valor = dadosValores.substring(dadosValores.lastIndexOf("$") + 2, dadosValores.lastIndexOf(',') + 3)
                .replaceAll("\\.", "").replaceAll(",", "\\.");

        return new BigDecimal(valor).multiply(new BigDecimal(this.parcelas));
    }

    public void setParcelado(BigDecimal parcelado) {
        this.parcelado = parcelado;
    }

    public int getParcelas(){
        return parcelas;
    }

    public int getParcelas(String dadosValores) {
        if (dadosValores.indexOf("até ") > 0) {
            return Integer
                    .parseInt(dadosValores.substring(dadosValores.indexOf("até ") + 4, dadosValores.indexOf("x de")));
        }
        return 0;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }
    
    @Override
    public String toString() {
        return "Produtos [descricao: " + descricao + ",\na Vista: " + aVista
                + ",\nparcelado: " + parcelado + ",\nparcelas: " + parcelas
                + "]\n---------------------------------------------------";
    }

}
