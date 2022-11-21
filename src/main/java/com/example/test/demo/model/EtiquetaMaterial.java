package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class EtiquetaMaterial {
    private List<Componente> componentes;
    private String etiqueta;
    private List<Integer> materiaisId;
    private String descircaoMatetial;
    private int quantidade;
    private int etiquetaId;

    public EtiquetaMaterial(List<Componente> componentes, String etiqueta, List<Integer> materiaisId, String descircaoMatetial, int quantidade,int etiquetaId) {
        this.componentes = componentes;
        this.etiqueta = etiqueta;
        this.etiquetaId = etiquetaId;
        this.materiaisId = materiaisId;
        this.descircaoMatetial = descircaoMatetial;
        this.quantidade = quantidade;
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
}
