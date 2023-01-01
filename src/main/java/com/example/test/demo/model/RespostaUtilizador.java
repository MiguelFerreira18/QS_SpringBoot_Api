package com.example.test.demo.model;

public class RespostaUtilizador extends Resposta{
    private boolean aceite;
    private static final int TIPO_RESPOSTA = 0;

    public RespostaUtilizador(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId,int pedidoId) {
        super(data, descricao, respostaId, TIPO_RESPOSTA, utilizadorId,pedidoId);
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

    @Override
    public int getTipoResposta() {
        return TIPO_RESPOSTA;
    }
}
