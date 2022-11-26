package com.example.test.demo.model;

public class PedidoLaboratorio extends Pedido {
    private int labId;
    private int authorId;

    public PedidoLaboratorio(int respostaId,int pedidoId, String tipoPedido, int labId, int autorId) {
        super(respostaId,pedidoId,tipoPedido);
        this.labId = labId;
        this.authorId = autorId;
    }

    public PedidoLaboratorio() {
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
