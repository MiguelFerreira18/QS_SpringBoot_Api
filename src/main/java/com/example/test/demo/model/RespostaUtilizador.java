package com.example.test.demo.model;

public class RespostaUtilizador extends Resposta{

    private static final int TIPO_RESPOSTA = 0;

    public RespostaUtilizador(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId,int pedidoId) {
        super(data, descricao, respostaId, TIPO_RESPOSTA, utilizadorId,pedidoId,aceite);
    }

    public RespostaUtilizador() {
    }


    @Override
    public int getTipoResposta() {
        return TIPO_RESPOSTA;
    }

}
