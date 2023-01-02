package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoMaterial extends Pedido{
    private List<Integer> materiais;
    private String dataReserva;
    private String dataEntrega;
    private int authorId;
    private final static String TIPO_MATERIAL = "pedidoMaterial";

    public PedidoMaterial(int pedidoId, int authorId, ArrayList<Integer> materiais, String dataReserva, String dataEntrega) {
        super(pedidoId,TIPO_MATERIAL);
        this.materiais = materiais;
        this.authorId = authorId;
        this.dataReserva = dataReserva;
        this.dataEntrega = dataEntrega;
    }


    public PedidoMaterial(int pedidoId, int authorId, String dataReserva, String dataEntrega) {
        super(pedidoId,TIPO_MATERIAL);
        this.materiais = new ArrayList<>();
        this.authorId = authorId;
        this.dataReserva = dataReserva;
        this.dataEntrega = dataEntrega;
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

    public String getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}

