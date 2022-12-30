package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class RespostaMaterial extends Resposta {
    private List<Integer> materiaisId;
    private String dataReserva;
    private String dataEntrega;
    private static final int TIPO_RESPOSTA = 2;

    public RespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId,int pedidoId, ArrayList<Integer> materiaisId) {
        super(data, descricao, respostaId, TIPO_RESPOSTA,utilizadorId,pedidoId);
        this.materiaisId = materiaisId;
        this.dataReserva = dataReserva;
        this.dataEntrega = dataEntrega;
    }
    public RespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId,int pedidoId) {
        super(data, descricao, respostaId, TIPO_RESPOSTA,utilizadorId,pedidoId);
        this.materiaisId = new ArrayList<>();
        this.dataReserva = dataReserva;
        this.dataEntrega = dataEntrega;
    }
    public RespostaMaterial() {
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

    @Override
    public int getTipoResposta() {
        return TIPO_RESPOSTA;
    }
}
