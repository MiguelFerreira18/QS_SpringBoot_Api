package com.example.test.demo.model;

public class PedidoLaboratorio extends Pedido {
    private int labId;
    private int authorId;
    private String date;
    private final static String TIPO_LABORATORIO = "pedidoLaboratorio";

    public PedidoLaboratorio(int pedidoId,int labId, int authorId,String date) {
        super(pedidoId,TIPO_LABORATORIO);
        this.labId = labId;
        this.authorId = authorId;
        this.date = date;

    }

    public PedidoLaboratorio() {
    }

    @Override
    public String getTipoPedido() {
        return TIPO_LABORATORIO;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
