package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Material {
    private int materialId;
    private String dataEntrega;
    private String descricao;
    private boolean isDisponivel;
    private boolean isAvariado;
    private int etiquetaId;//È POSSIVEL QUE SEJA NECESSÀRIO REMOVER ESTA VARIAVEL; OU NA ETIQUETA A LISTA DE MATERIAIS
    private ArrayList<String> uniadadesCurriculares;
    private List<Integer> respostasMaterial;

    public Material(String descricao, boolean isDisponivel, boolean isAvariado, int etiquetaId ){
        this.materialId = 0;
        this.dataEntrega = "null";
        this.descricao = descricao;
        this.isDisponivel = isDisponivel;
        this.isAvariado = isAvariado;
        this.etiquetaId = etiquetaId;
        respostasMaterial = new ArrayList<>();
        uniadadesCurriculares = new ArrayList<>();
    }

    public Material() {
    }

    public int getEtiquetaId() {
        return etiquetaId;
    }
    
    public void setEtiquetaId(int etiquetaId) {
        this.etiquetaId = etiquetaId;
    }

    public List<Integer> getRespostasMaterial() {
        return respostasMaterial;
    }

    public void setRespostasMaterial(List<Integer> respostasMaterial) {
        this.respostasMaterial = respostasMaterial;
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

    public ArrayList<String> getUniadadesCurriculares() {
        return uniadadesCurriculares;
    }

    public void setUniadadesCurriculares(ArrayList<String> uniadadesCurriculares) {
        this.uniadadesCurriculares = uniadadesCurriculares;
    }
}
