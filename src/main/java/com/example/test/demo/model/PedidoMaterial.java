package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    private List<Integer> materiais;
    private int authorId;
    private final static String TIPO_MATERIAL = "pedidoMaterial";

    public PedidoMaterial(int respostaId,int pedidoId, int authorId, ArrayList<>() materiais) {
        super(respostaId,pedidoId,TIPO_MATERIAL);
        this.materiais = materiais;
        this.authorId = autorId;

    }

    public PedidoMaterial() {
    }


    @Override
    public String getTipoPedido() {
        return TIPO_MATERIAL;
    }

    public List<Integer> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Integer> materiais) {
        this.materiais = materiais;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}

