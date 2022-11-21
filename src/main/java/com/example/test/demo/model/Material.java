package com.example.test.demo.model;

public class Material {
    private String dataEntrega;
    private String descricao;
    private boolean isDisponivel;
    private boolean isAvariado;
    private int materialId;

    public Material(String dataEntrega, String descricao, boolean isDisponivel, boolean isAvariado, int materialId) {
        this.dataEntrega = dataEntrega;
        this.descricao = descricao;
        this.isDisponivel = isDisponivel;
        this.isAvariado = isAvariado;
        this.materialId = materialId;
    }
    public Material() {
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean disponivel) {
        isDisponivel = disponivel;
    }

    public boolean isAvariado() {
        return isAvariado;
    }

    public void setAvariado(boolean avariado) {
        isAvariado = avariado;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

}
