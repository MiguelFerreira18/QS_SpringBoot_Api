package com.example.test.demo.model;

public class PedidoUtilizador extends Pedido{

    private String descricao;
    private int docenteId;
    private static String TIPO_UTILIZADOR = "pedidoUtilizador";

    public PedidoUtilizador(int pedidoId,String descricao,int docenteId) {
        super(pedidoId,TIPO_UTILIZADOR);
        this.descricao = descricao;
        this.docenteId = docenteId;
    }

    public PedidoUtilizador() {
    }

    @Override
    public String getTipoPedido() {
        return TIPO_UTILIZADOR;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }
}
