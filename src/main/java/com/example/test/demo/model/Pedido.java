package com.example.test.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Pedido {

    private int pedidoId;
    private String dataPedido;

    private boolean resposta;
    private String tipoPedido;
    private int respostaId;

    public Pedido(int respostaId,int pedidoId, String tipoPedido) {


        this.resposta = false;
        this.tipoPedido = tipoPedido;
        this.dataPedido = LocalDate.now().toString();
        this.pedidoId = pedidoId;
        this.respostaId = respostaId;
    }

    public Pedido() {
    }

    public int getRespostaId() {
        return respostaId;
    }

    public void setRespostaId(int respostaId) {
        this.respostaId = respostaId;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
}
