package com.example.test.demo.model;

import java.util.List;

public class Resposta {
    private String data;
    private String descricao;
    private List<Integer> materiais;
    private int respostaId;
    public Resposta(int respostaId,String data, String descricao, List<Integer> materiais) {
        this.data = data;
        this.descricao = descricao;
        this.materiais = materiais;
        this.respostaId = respostaId;
    }

    public Resposta() {
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

    public List<Integer> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Integer> materiais) {
        this.materiais = materiais;
    }
}
