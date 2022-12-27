package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class EtiquetaMaterial {
    private List<Integer> componentes;
    private String etiqueta;
    private String subEtiqueta;
    private List<Integer> materiaisId;
    private String descricaoMaterial;
    private int quantidade;
    private int etiquetaId;

    public EtiquetaMaterial(ArrayList<Integer> componentes,String etiqueta, String descricaoMaterial, int quantidade, int etiquetaId, String subEtiqueta, ArrayList<Integer> materiaisId) {
        this.componentes = componentes;
        this.etiqueta = etiqueta;
        this.etiquetaId = etiquetaId;
        this.materiaisId = materiaisId;
        this.descricaoMaterial = descricaoMaterial;
        this.quantidade = quantidade;
        this.subEtiqueta = subEtiqueta;
    }

    public EtiquetaMaterial(String etiqueta, String subEtiqueta, String descricaoMaterial, int quantidade, int etiquetaId) {
        this.componentes = new ArrayList<>();
        this.etiqueta = etiqueta;
        this.subEtiqueta = subEtiqueta;
        this.materiaisId = new ArrayList<>();
        this.descricaoMaterial = descricaoMaterial;
        this.quantidade = quantidade;
        this.etiquetaId = etiquetaId;
    }

    public EtiquetaMaterial() {
    }

    public int getEtiquetaId() {
        return etiquetaId;
    }

    public void setEtiquetaId(int etiquetaId) {
        this.etiquetaId = etiquetaId;
    }

    public List<Integer> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Integer> componentes) {
        this.componentes = componentes;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public List<Integer> getMateriaisId() {
        return materiaisId;
    }

    public void setMateriaisId(List<Integer> materiaisId) {
        this.materiaisId = materiaisId;
    }

    public String getDescricaoMaterial() {
        return descricaoMaterial;
    }

    public void setDescricaoMaterial(String descricaoMaterial) {
        this.descricaoMaterial = descricaoMaterial;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getSubEtiqueta() {
        return subEtiqueta;
    }

    public void setSubEtiqueta(String subEtiqueta) {
        this.subEtiqueta = subEtiqueta;
    }
}
