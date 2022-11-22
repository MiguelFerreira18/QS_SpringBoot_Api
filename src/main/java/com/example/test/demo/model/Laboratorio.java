package com.example.test.demo.model;

import java.util.List;

public class Laboratorio {
    private List<String> logs;
    private int laboratorioId;
    private List<Integer> materiaisId;
    private int refAdmin;

    public Laboratorio(List<String> logs, int laboratorioId, List<Integer> materiaisId, int refAdmin) {
        this.logs = logs;
        this.laboratorioId = laboratorioId;
        this.materiaisId = materiaisId;
        this.refAdmin = refAdmin;
    }

    public Laboratorio() {
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
