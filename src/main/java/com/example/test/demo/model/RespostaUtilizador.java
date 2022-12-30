package com.example.test.demo.model;

public class RespostaUtilizador extends Resposta{
    private boolean aceite;
    private String nomeUtilizador;//PERGUNTAR SE PODE FICAR ASSIM PARA ALTERAR ESTADOS DO UTILIZADOR
    private static final int TIPO_RESPOSTA = 0;

    public RespostaUtilizador(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId,int pedidoId, String nomeUtilizador) {
        super(data, descricao, respostaId, TIPO_RESPOSTA, utilizadorId,pedidoId);
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
    @Override
    public int getTipoResposta() {
        return TIPO_RESPOSTA;
    }
}
