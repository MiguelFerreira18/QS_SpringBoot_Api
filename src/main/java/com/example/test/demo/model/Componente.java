package com.example.test.demo.model;

public class Componente {
    private int id;
    private String descricao;
    private int quantidade;

    public Componente(int id,String descricao, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Componente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
