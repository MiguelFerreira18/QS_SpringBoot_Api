package com.example.test.demo.model;

public class RespostaUtilizador extends Resposta{
    private boolean aceite;

    public RespostaUtilizador(String data, String descricao, int respostaId, int tipoResposta,  boolean aceite,int utilizadorId) {
        super(data, descricao, respostaId, tipoResposta, utilizadorId);
        this.aceite = aceite;
    }

    public RespostaUtilizador() {
    }

    public boolean isAceite() {
        return aceite;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }
}
