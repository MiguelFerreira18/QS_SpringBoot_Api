package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {
    private List<String> logs;
    private int laboratorioId;
    private List<Integer> materiaisId;
    private int refAdmin;
    private List<Integer> respostasLaboratorio;



    public Laboratorio( int laboratorioId, int refAdmin) {
        this.logs = new ArrayList<>();
        this.laboratorioId = laboratorioId;
        this.materiaisId = new ArrayList<>();
        this.refAdmin = refAdmin;
        respostasLaboratorio = new ArrayList<>();
    }
    public Laboratorio(ArrayList<String> logs,int laboratorioId,  ArrayList<Integer> materiaisId,int refAdmin,ArrayList<Integer> respostasLaboratorio) {
        this.logs = logs;
        this.laboratorioId = laboratorioId;
        this.materiaisId = materiaisId;
        this.refAdmin = refAdmin;
        this.respostasLaboratorio = respostasLaboratorio;
    }


    public Laboratorio() {
    }

    public List<Integer> getRespostasLaboratorio() {
        return respostasLaboratorio;
    }

    public void setRespostasLaboratorio(List<Integer> respostasLaboratorio) {
        this.respostasLaboratorio = respostasLaboratorio;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public int getLaboratorioId() {
        return laboratorioId;
    }

    public void setLaboratorioId(int laboratorioId) {
        this.laboratorioId = laboratorioId;
    }

    public List<Integer> getMateriaisId() {
        return materiaisId;
    }

    public void setMateriaisId(List<Integer> materiaisId) {
        this.materiaisId = materiaisId;
    }

    public int getRefAdmin() {
        return refAdmin;
    }

    public void setRefAdmin(int refAdmin) {
        this.refAdmin = refAdmin;
    }
}
