package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    List<String> etiquetas;

    public PedidoMaterial(int respostaId,int pedidoId, String tipoPedido, int materialId) {
        super(respostaId,pedidoId,tipoPedido);
        etiquetas = new ArrayList<>();
    }

    public PedidoMaterial() {
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}

