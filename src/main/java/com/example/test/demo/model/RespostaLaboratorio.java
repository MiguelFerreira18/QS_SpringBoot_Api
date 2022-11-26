package com.example.test.demo.model;

public class RespostaLaboratorio extends Resposta{
    private int laboratorioId;
    private String dataReservaInicio;
    private String dataReservaFim;

    public RespostaLaboratorio(String data, String descricao, int respostaId, int tipoResposta, int laboratorioId, String dataReservaInicio,boolean isAceite ,String dataReservaFim,int utilizadorId) {
        super(data, descricao, respostaId, tipoResposta,utilizadorId,isAceite);
        this.laboratorioId = laboratorioId;
        this.dataReservaInicio = dataReservaInicio;
        this.dataReservaFim = dataReservaFim;
    }

    public int getLaboratorioId() {
        return laboratorioId;
    }

    public void setLaboratorioId(int laboratorioId) {
        this.laboratorioId = laboratorioId;
    }

    public String getDataReservaInicio() {
        return dataReservaInicio;
    }

    public void setDataReservaInicio(String dataReservaInicio) {
        this.dataReservaInicio = dataReservaInicio;
    }

    public String getDataReservaFim() {
        return dataReservaFim;
    }

    public void setDataReservaFim(String dataReservaFim) {
        this.dataReservaFim = dataReservaFim;
    }
}
