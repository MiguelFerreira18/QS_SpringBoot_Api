package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Material {
    private int materialId;
    private String descricao;
    private boolean disponivel;
    private boolean avariado;
    private int etiquetaId;//È POSSIVEL QUE SEJA NECESSÀRIO REMOVER ESTA VARIAVEL; OU NA ETIQUETA A LISTA DE MATERIAIS
    private List<String> unidadesCurriculares;
    private List<Integer> respostasMaterial;

    public Material(String descricao, boolean disponivel, boolean avariado, int materialId, int etiquetaId, ArrayList<String> unidadesCurriculares, ArrayList<Integer> respostasMaterial) {
        this.materialId = materialId;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.avariado = avariado;
        this.etiquetaId = etiquetaId;
        unidadesCurriculares = unidadesCurriculares;
        respostasMaterial = respostasMaterial;
    }

    public Material(String descricao, boolean disponivel, boolean avariado, int materialId, int etiquetaId) {
        this.materialId = materialId;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.avariado = avariado;
        this.etiquetaId = etiquetaId;
        this.unidadesCurriculares = new ArrayList<>();
        this.respostasMaterial = new ArrayList<>();
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isAvariado() {
        return avariado;
    }

    public void setAvariado(boolean avariado) {
        this.avariado = avariado;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public List<String> getUnidadesCurriculares() {
        return unidadesCurriculares;
    }

    public void setUnidadesCurriculares(ArrayList<String> unidadesCurriculares) {
        this.unidadesCurriculares = unidadesCurriculares;
    }
}
