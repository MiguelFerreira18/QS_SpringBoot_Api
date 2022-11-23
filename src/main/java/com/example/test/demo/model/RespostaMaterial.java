package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class RespostaMaterial extends Resposta{
    private List<Integer> materiaisId;
    private String dataReserva;
    private String dataEntrega;

    public RespostaMaterial(String data, String descricao, int respostaId, int tipoResposta, String dataReserva, String dataEntrega) {
        super(data, descricao, respostaId, tipoResposta);
        this.materiaisId = new ArrayList<>();
        this.dataReserva = dataReserva;
        this.dataEntrega = dataEntrega;
    }
    public List<Integer> getMateriaisId() {
        return materiaisId;
    }

    public void setMateriaisId(List<Integer> materiaisId) {
        this.materiaisId = materiaisId;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
