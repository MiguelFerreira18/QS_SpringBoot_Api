package com.example.test.demo.model;

public class RespostaLaboratorio extends Resposta{
    private int laboratorioId;
    private String dataReservaInicio;
    private String dataReservaFim;
    private static final int TIPO_RESPOSTA = 1;

    public RespostaLaboratorio(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) {
        super(data, descricao, respostaId, TIPO_RESPOSTA, utilizadorId);
        this.laboratorioId = laboratorioId;
        this.dataReservaInicio = dataReservaInicio;
        this.dataReservaFim = dataReservaFim;
    }

    public RespostaLaboratorio() {
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
    @Override
    public int getTipoResposta() {
        return TIPO_RESPOSTA;
    }
}
