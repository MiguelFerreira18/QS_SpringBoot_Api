package com.example.test.demo.model;

public class PedidoUtilizador extends Pedido{

    private String descricao;


    public PedidoUtilizador(int respostaId,int pedidoId, String tipoPedido, String descricao) {
        super(respostaId,pedidoId,tipoPedido);

        this.descricao = descricao;
    }

    public PedidoUtilizador() {
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
