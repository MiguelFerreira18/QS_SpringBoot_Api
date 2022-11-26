package com.example.test.demo.model;

public class RespostaUtilizador extends Resposta{
    private boolean aceite;
    private String nomeUtilizador;//PERGUNTAR SE PODE FICAR ASSIM PARA ALTERAR ESTADOS DO UTILIZADOR

    public RespostaUtilizador(String data, String descricao, int respostaId, int tipoResposta,  boolean aceite,int utilizadorId, String nomeUtilizador) {
        super(data, descricao, respostaId, tipoResposta, utilizadorId);
        this.aceite = aceite;
        this.nomeUtilizador = nomeUtilizador;
    }

    public RespostaUtilizador() {
    }

    public boolean isAceite() {
        return aceite;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }

    public String getNomeUtilizador() {
        return nomeUtilizador;
    }

    public void setNomeUtilizador(String nomeUtilizador) {
        this.nomeUtilizador = nomeUtilizador;
    }
}
