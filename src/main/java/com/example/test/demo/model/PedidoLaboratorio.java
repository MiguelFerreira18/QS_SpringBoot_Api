package com.example.test.demo.model;

public class PedidoLaboratorio extends Pedido {
    private int labId;
    private int authorId;
    private final static String TIPO_LABORATORIO = "pedidoLaboratorio";

    public PedidoLaboratorio(int respostaId,int pedidoId, int labId, int authorId) {
        super(respostaId,pedidoId,TIPO_LABORATORIO);
        this.labId = labId;
        this.authorId = authorId;
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
}
