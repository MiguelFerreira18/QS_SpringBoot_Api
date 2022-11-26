package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    private List<String> etiquetas;
    private int authorId;

    public PedidoMaterial(int respostaId,int pedidoId, String tipoPedido, int autorId) {
        super(respostaId,pedidoId,tipoPedido);
        etiquetas = new ArrayList<>();
        this.authorId = autorId;
    }

    public PedidoMaterial() {
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}

