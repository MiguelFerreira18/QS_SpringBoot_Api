package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    private List<Integer> materiais;
    private String dataDeInicio;
    private String dataFim;
    private int authorId;
    private final static String TIPO_MATERIAL = "pedidoMaterial";

    public PedidoMaterial(int respostaId,int pedidoId, int authorId, ArrayList<Integer> materiais, String dataDeInicio, String dataFim) {
        super(respostaId,pedidoId,TIPO_MATERIAL);
        this.materiais = materiais;
        this.authorId = authorId;
        this.dataDeInicio = dataDeInicio;
        this.dataFim = dataFim;
    }


    public PedidoMaterial(int respostaId,int pedidoId, int authorId, String dataDeInicio, String dataFim) {
        super(respostaId,pedidoId,TIPO_MATERIAL);
        this.materiais = new ArrayList<>();
        this.authorId = authorId;
        this.dataDeInicio = dataDeInicio;
        this.dataFim = dataFim;
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

