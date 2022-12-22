package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    private List<String> etiquetas;
    private int authorId;
    private final static String TIPO_MATERIAL = "pedidoMaterial";

    public PedidoMaterial(int respostaId,int pedidoId, int autorId) {
        super(respostaId,pedidoId,TIPO_MATERIAL);
        etiquetas = new ArrayList<>();
        this.authorId = autorId;
    }

    public PedidoMaterial() {
    }


    @Override
    public String getTipoPedido() {
        return TIPO_MATERIAL;
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

