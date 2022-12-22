package com.example.test.demo.model;

import java.time.LocalDate;

public class Wish {
    private int idWish;
    private int idDocente;
    private String nomeMaterial;
    private String descricaoMaterial;
    private String date;

    public Wish(int idWish,String nomeMaterial, String descricaoMaterial,int idDocente) {
        this.idWish = idWish;
        this.idDocente = idDocente;
        this.nomeMaterial = nomeMaterial;
        this.descricaoMaterial = descricaoMaterial;
        this.date = LocalDate.now().toString();

    }
    public Wish() {
    }

    public String getNomeMaterial() {
        return nomeMaterial;
    }

    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }

    public String getDescricaoMaterial() {
        return descricaoMaterial;
    }

    public void setDescricaoMaterial(String descricaoMaterial) {
        this.descricaoMaterial = descricaoMaterial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdWish() {
        return idWish;
    }

    public void setIdWish(int idWish) {
        this.idWish = idWish;
    }
}
