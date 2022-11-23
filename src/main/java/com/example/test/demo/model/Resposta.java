package com.example.test.demo.model;

import java.util.List;

public abstract class Resposta {
    private String data;
    private String descricao;

    private int respostaId;
    private int tipoResposta;
    private int utilizadorId;

    //0=pedidos do utilizador, 1=pedidos do laboratorio,2=pedidos do material

    public Resposta(String data, String descricao, int respostaId, int tipoResposta) {
        this.data = data;
        this.descricao = descricao;
        this.respostaId = respostaId;
        this.tipoResposta = tipoResposta;
    }

    public Resposta() {
    }

    public int getTipoResposta() {
        return tipoResposta;
    }

    public void setTipoResposta(int tipoResposta) {
        this.tipoResposta = tipoResposta;
    }

    public int getRespostaId() {
        return respostaId;
    }

    public void setRespostaId(int respostaId) {
        this.respostaId = respostaId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
