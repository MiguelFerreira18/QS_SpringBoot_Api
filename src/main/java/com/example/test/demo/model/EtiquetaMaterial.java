package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class EtiquetaMaterial {
    private List<Componente> componentes;
    private String etiqueta;
    private String subEtiqueta;
    private List<Integer> materiaisId;
    private String descircaoMatetial;
    private int quantidade;
    private int etiquetaId;

    public EtiquetaMaterial( String etiqueta,  String descircaoMatetial, int quantidade,int etiquetaId, String subEtiqueta) {
        this.componentes = new ArrayList<>();
        this.etiqueta = etiqueta;
        this.etiquetaId = etiquetaId;
        this.materiaisId = new ArrayList<>();
        this.descircaoMatetial = descircaoMatetial;
        this.quantidade = quantidade;
        this.subEtiqueta = subEtiqueta;
    }

    public EtiquetaMaterial() {
    }

    public int getEtiquetaId() {
        return etiquetaId;
    }

    public void setEtiquetaId(int etiquetaId) {
        this.etiquetaId = etiquetaId;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
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

    public String getDescircaoMatetial() {
        return descircaoMatetial;
    }

    public void setDescircaoMatetial(String descircaoMatetial) {
        this.descircaoMatetial = descircaoMatetial;
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
