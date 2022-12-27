package com.example.test.demo.model;

public class PedidoUtilizador extends Pedido{

    private String descricao;
    private static String TIPO_UTILIZADOR = "pedidoUtilizador";

    public PedidoUtilizador(int respostaId,int pedidoId,String descricao) {
        super(respostaId,pedidoId,TIPO_UTILIZADOR);
        this.descricao = descricao;
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

}
