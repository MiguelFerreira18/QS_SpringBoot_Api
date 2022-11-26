package com.example.test.demo.model;

import java.util.List;

public class PedidoLaboratorio extends Pedido {
    private int labId;

    public PedidoLaboratorio(int respostaId,int pedidoId, String tipoPedido, int labId) {
        super(respostaId,pedidoId,tipoPedido);
        this.labId = labId;
    }

    public PedidoLaboratorio() {
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

}
