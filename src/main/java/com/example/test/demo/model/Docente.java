package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Docente {
    private String docenteNome;
    private String docenteEmail;
    private String docentePassword;
    private int docenteNumber;
    private List<String> unidadesCurriculares;
    private boolean isAdmin;
    private boolean hasAccess;


    public Docente(boolean hasAccess,String docenteNome, String docenteEmail, String docentePassword, int docenteId, boolean isAdmin) {
        this.docenteNome = docenteNome;
        this.docenteEmail = docenteEmail;
        this.docentePassword = docentePassword;
        this.docenteNumber = docenteId;
        this.unidadesCurriculares = new ArrayList<>() ;
        this.isAdmin = isAdmin;
        this.hasAccess = hasAccess;
    }
    public Docente() {
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    public String getDocenteNome() {
        return docenteNome;
    }

    public void setDocenteNome(String docenteNome) {
        this.docenteNome = docenteNome;
    }

    public String getDocenteEmail() {
        return docenteEmail;
    }

    public void setDocenteEmail(String docenteEmail) {
        this.docenteEmail = docenteEmail;
    }

    public String getDocentePassword() {
        return docentePassword;
    }

    public void setDocentePassword(String docentePassword) {
        this.docentePassword = docentePassword;
    }

    public int getDocenteNumber() {
        return docenteNumber;
    }

    public void setDocenteNumber(int docenteNumber) {
        this.docenteNumber = docenteNumber;
    }

    public List<String> getUnidadesCurriculares() {
        return unidadesCurriculares;
    }

    public void setUnidadesCurriculares(List<String> unidadesCurriculares) {
        this.unidadesCurriculares = unidadesCurriculares;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}